--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3 (Debian 16.3-1.pgdg120+1)
-- Dumped by pg_dump version 16.3 (Debian 16.3-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: client; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.client (
    id bigint NOT NULL,
    name character varying(150) NOT NULL,
    cpf character varying(11) NOT NULL,
    phone character varying(20) NOT NULL,
    email character varying(254),
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT chk_client_cpf_digits CHECK (((cpf)::text ~ '^[0-9]{11}$'::text)),
    CONSTRAINT chk_client_email_format CHECK (((email IS NULL) OR ((email)::text ~* '^[^[:space:]@]+@[^[:space:]@]+\.[^[:space:]@]+$'::text))),
    CONSTRAINT chk_client_email_not_blank CHECK (((email IS NULL) OR (btrim((email)::text) <> ''::text))),
    CONSTRAINT chk_client_name_not_blank CHECK ((btrim((name)::text) <> ''::text)),
    CONSTRAINT chk_client_phone_not_blank CHECK ((btrim((phone)::text) <> ''::text))
);


--
-- Name: client_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.client ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.client_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: part; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.part (
    id bigint NOT NULL,
    name character varying(150) NOT NULL,
    description text,
    unit_price numeric(10,2) NOT NULL,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT chk_part_description_not_blank CHECK (((description IS NULL) OR (btrim(description) <> ''::text))),
    CONSTRAINT chk_part_name_not_blank CHECK ((btrim((name)::text) <> ''::text)),
    CONSTRAINT chk_part_unit_price CHECK ((unit_price >= (0)::numeric))
);


--
-- Name: part_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.part ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.part_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: professional; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.professional (
    id bigint NOT NULL,
    name character varying(150) NOT NULL,
    cpf character varying(11) NOT NULL,
    phone character varying(20) NOT NULL,
    email character varying(254),
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT chk_professional_cpf_digits CHECK (((cpf)::text ~ '^[0-9]{11}$'::text)),
    CONSTRAINT chk_professional_email_format CHECK (((email IS NULL) OR ((email)::text ~* '^[^[:space:]@]+@[^[:space:]@]+\.[^[:space:]@]+$'::text))),
    CONSTRAINT chk_professional_email_not_blank CHECK (((email IS NULL) OR (btrim((email)::text) <> ''::text))),
    CONSTRAINT chk_professional_name_not_blank CHECK ((btrim((name)::text) <> ''::text)),
    CONSTRAINT chk_professional_phone_not_blank CHECK ((btrim((phone)::text) <> ''::text))
);


--
-- Name: professional_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.professional ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.professional_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: professional_role; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.professional_role (
    professional_id bigint NOT NULL,
    role_id bigint NOT NULL
);


--
-- Name: role; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.role (
    id bigint NOT NULL,
    name character varying(50) NOT NULL,
    description character varying(255),
    CONSTRAINT chk_role_description_not_blank CHECK (((description IS NULL) OR (btrim((description)::text) <> ''::text))),
    CONSTRAINT chk_role_name_not_blank CHECK ((btrim((name)::text) <> ''::text))
);


--
-- Name: role_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.role ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: service_item; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.service_item (
    id bigint NOT NULL,
    name character varying(150) NOT NULL,
    description text,
    base_price numeric(10,2) NOT NULL,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT chk_service_item_base_price CHECK ((base_price >= (0)::numeric)),
    CONSTRAINT chk_service_item_description_not_blank CHECK (((description IS NULL) OR (btrim(description) <> ''::text))),
    CONSTRAINT chk_service_item_name_not_blank CHECK ((btrim((name)::text) <> ''::text))
);


--
-- Name: service_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.service_item ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.service_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: service_order; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.service_order (
    id bigint NOT NULL,
    vehicle_id bigint NOT NULL,
    client_id bigint NOT NULL,
    professional_id bigint NOT NULL,
    status character varying(20) NOT NULL,
    reported_problem text NOT NULL,
    diagnosis text,
    odometer integer NOT NULL,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    completed_at timestamp with time zone,
    cancelled_at timestamp with time zone,
    cancellation_reason text,
    CONSTRAINT chk_service_order_cancellation_reason CHECK (((cancellation_reason IS NULL) OR (btrim(cancellation_reason) <> ''::text))),
    CONSTRAINT chk_service_order_cancelled_after_created CHECK (((cancelled_at IS NULL) OR (cancelled_at >= created_at))),
    CONSTRAINT chk_service_order_cancelled_at_consistency CHECK ((((status)::text = 'CANCELLED'::text) OR ((cancelled_at IS NULL) AND (cancellation_reason IS NULL)))),
    CONSTRAINT chk_service_order_cancelled_status CHECK ((((status)::text <> 'CANCELLED'::text) OR ((cancelled_at IS NOT NULL) AND (cancellation_reason IS NOT NULL)))),
    CONSTRAINT chk_service_order_completed_after_created CHECK (((completed_at IS NULL) OR (completed_at >= created_at))),
    CONSTRAINT chk_service_order_completed_at_consistency CHECK ((((status)::text = 'COMPLETED'::text) OR (completed_at IS NULL))),
    CONSTRAINT chk_service_order_completed_status CHECK ((((status)::text <> 'COMPLETED'::text) OR (completed_at IS NOT NULL))),
    CONSTRAINT chk_service_order_diagnosis CHECK (((diagnosis IS NULL) OR (btrim(diagnosis) <> ''::text))),
    CONSTRAINT chk_service_order_odometer CHECK ((odometer >= 0)),
    CONSTRAINT chk_service_order_reported_problem CHECK ((btrim(reported_problem) <> ''::text)),
    CONSTRAINT chk_service_order_status CHECK (((status)::text = ANY ((ARRAY['OPEN'::character varying, 'IN_PROGRESS'::character varying, 'COMPLETED'::character varying, 'CANCELLED'::character varying])::text[])))
);


--
-- Name: service_order_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.service_order ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.service_order_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: service_order_part; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.service_order_part (
    id bigint NOT NULL,
    service_order_id bigint NOT NULL,
    part_id bigint NOT NULL,
    quantity_reserved integer NOT NULL,
    quantity_used integer DEFAULT 0 NOT NULL,
    unit_price numeric(10,2) NOT NULL,
    CONSTRAINT chk_service_order_part_quantity_reserved CHECK ((quantity_reserved > 0)),
    CONSTRAINT chk_service_order_part_quantity_used CHECK ((quantity_used >= 0)),
    CONSTRAINT chk_service_order_part_unit_price CHECK ((unit_price >= (0)::numeric)),
    CONSTRAINT chk_service_order_part_used_not_exceed_reserved CHECK ((quantity_used <= quantity_reserved))
);


--
-- Name: service_order_part_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.service_order_part ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.service_order_part_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: service_order_service_item; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.service_order_service_item (
    service_order_id bigint NOT NULL,
    service_item_id bigint NOT NULL,
    unit_price numeric(10,2) NOT NULL,
    CONSTRAINT chk_service_order_service_unit_price CHECK ((unit_price >= (0)::numeric))
);


--
-- Name: stock; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.stock (
    id bigint NOT NULL,
    part_id bigint NOT NULL,
    quantity_on_hand integer DEFAULT 0 NOT NULL,
    quantity_reserved integer DEFAULT 0 NOT NULL,
    updated_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT chk_stock_quantity_on_hand CHECK ((quantity_on_hand >= 0)),
    CONSTRAINT chk_stock_quantity_reserved CHECK ((quantity_reserved >= 0)),
    CONSTRAINT chk_stock_reserved_not_exceed_on_hand CHECK ((quantity_reserved <= quantity_on_hand))
);


--
-- Name: stock_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.stock ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.stock_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: stock_moviment; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.stock_moviment (
    id bigint NOT NULL,
    stock_id bigint NOT NULL,
    service_order_part_id bigint,
    movement_type character varying(30) NOT NULL,
    quantity integer NOT NULL,
    description text,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    part_name character varying(255),
    unit_price numeric(10,2),
    CONSTRAINT chk_stock_moviment_description CHECK (((description IS NULL) OR (btrim(description) <> ''::text))),
    CONSTRAINT chk_stock_moviment_quantity CHECK ((quantity > 0)),
    CONSTRAINT chk_stock_moviment_type CHECK (((movement_type)::text = ANY ((ARRAY['ENTRY'::character varying, 'RESERVATION'::character varying, 'CONSUMPTION'::character varying, 'RELEASE'::character varying, 'ADJUSTMENT'::character varying])::text[])))
);


--
-- Name: stock_moviment_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.stock_moviment ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.stock_moviment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: vehicle; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.vehicle (
    id bigint NOT NULL,
    plate character varying(10) NOT NULL,
    brand character varying(60) NOT NULL,
    model character varying(100) NOT NULL,
    proprietor character varying(150) NOT NULL,
    CONSTRAINT chk_vehicle_brand_not_blank CHECK ((btrim((brand)::text) <> ''::text)),
    CONSTRAINT chk_vehicle_model_not_blank CHECK ((btrim((model)::text) <> ''::text)),
    CONSTRAINT chk_vehicle_plate_not_blank CHECK ((btrim((plate)::text) <> ''::text)),
    CONSTRAINT chk_vehicle_proprietor_not_blank CHECK ((btrim((proprietor)::text) <> ''::text))
);


--
-- Name: vehicle_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.vehicle ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.vehicle_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: client pk_client; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT pk_client PRIMARY KEY (id);


--
-- Name: part pk_part; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.part
    ADD CONSTRAINT pk_part PRIMARY KEY (id);


--
-- Name: professional pk_professional; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.professional
    ADD CONSTRAINT pk_professional PRIMARY KEY (id);


--
-- Name: professional_role pk_professional_role; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.professional_role
    ADD CONSTRAINT pk_professional_role PRIMARY KEY (professional_id, role_id);


--
-- Name: role pk_role; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT pk_role PRIMARY KEY (id);


--
-- Name: service_item pk_service_item; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.service_item
    ADD CONSTRAINT pk_service_item PRIMARY KEY (id);


--
-- Name: service_order pk_service_order; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.service_order
    ADD CONSTRAINT pk_service_order PRIMARY KEY (id);


--
-- Name: service_order_part pk_service_order_part; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.service_order_part
    ADD CONSTRAINT pk_service_order_part PRIMARY KEY (id);


--
-- Name: service_order_service_item pk_service_order_service; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.service_order_service_item
    ADD CONSTRAINT pk_service_order_service PRIMARY KEY (service_order_id, service_item_id);


--
-- Name: stock pk_stock; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.stock
    ADD CONSTRAINT pk_stock PRIMARY KEY (id);


--
-- Name: stock_moviment pk_stock_moviment; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.stock_moviment
    ADD CONSTRAINT pk_stock_moviment PRIMARY KEY (id);


--
-- Name: vehicle pk_vehicle; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.vehicle
    ADD CONSTRAINT pk_vehicle PRIMARY KEY (id);


--
-- Name: client uq_client_cpf; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT uq_client_cpf UNIQUE (cpf);


--
-- Name: part uq_part_name; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.part
    ADD CONSTRAINT uq_part_name UNIQUE (name);


--
-- Name: professional uq_professional_cpf; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.professional
    ADD CONSTRAINT uq_professional_cpf UNIQUE (cpf);


--
-- Name: role uq_role_name; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT uq_role_name UNIQUE (name);


--
-- Name: service_item uq_service_item_name; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.service_item
    ADD CONSTRAINT uq_service_item_name UNIQUE (name);


--
-- Name: service_order_part uq_service_order_part; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.service_order_part
    ADD CONSTRAINT uq_service_order_part UNIQUE (service_order_id, part_id);


--
-- Name: stock uq_stock_part; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.stock
    ADD CONSTRAINT uq_stock_part UNIQUE (part_id);


--
-- Name: vehicle uq_vehicle_plate; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.vehicle
    ADD CONSTRAINT uq_vehicle_plate UNIQUE (plate);


--
-- Name: professional_role fk_professional_role_professional; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.professional_role
    ADD CONSTRAINT fk_professional_role_professional FOREIGN KEY (professional_id) REFERENCES public.professional(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: professional_role fk_professional_role_role; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.professional_role
    ADD CONSTRAINT fk_professional_role_role FOREIGN KEY (role_id) REFERENCES public.role(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: service_order fk_service_order_client; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.service_order
    ADD CONSTRAINT fk_service_order_client FOREIGN KEY (client_id) REFERENCES public.client(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: service_order_part fk_service_order_part_order; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.service_order_part
    ADD CONSTRAINT fk_service_order_part_order FOREIGN KEY (service_order_id) REFERENCES public.service_order(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: service_order_part fk_service_order_part_part; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.service_order_part
    ADD CONSTRAINT fk_service_order_part_part FOREIGN KEY (part_id) REFERENCES public.part(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: service_order fk_service_order_professional; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.service_order
    ADD CONSTRAINT fk_service_order_professional FOREIGN KEY (professional_id) REFERENCES public.professional(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: service_order_service_item fk_service_order_service_order; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.service_order_service_item
    ADD CONSTRAINT fk_service_order_service_order FOREIGN KEY (service_order_id) REFERENCES public.service_order(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: service_order_service_item fk_service_order_service_service_item; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.service_order_service_item
    ADD CONSTRAINT fk_service_order_service_service_item FOREIGN KEY (service_item_id) REFERENCES public.service_item(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: service_order fk_service_order_vehicle; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.service_order
    ADD CONSTRAINT fk_service_order_vehicle FOREIGN KEY (vehicle_id) REFERENCES public.vehicle(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: stock_moviment fk_stock_moviment_service_order_part; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.stock_moviment
    ADD CONSTRAINT fk_stock_moviment_service_order_part FOREIGN KEY (service_order_part_id) REFERENCES public.service_order_part(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: stock_moviment fk_stock_moviment_stock; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.stock_moviment
    ADD CONSTRAINT fk_stock_moviment_stock FOREIGN KEY (stock_id) REFERENCES public.stock(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: stock fk_stock_part; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.stock
    ADD CONSTRAINT fk_stock_part FOREIGN KEY (part_id) REFERENCES public.part(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- PostgreSQL database dump complete
--

