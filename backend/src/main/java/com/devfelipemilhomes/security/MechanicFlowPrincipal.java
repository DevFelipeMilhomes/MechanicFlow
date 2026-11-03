package com.devfelipemilhomes.security;

import java.util.List;

public interface MechanicFlowPrincipal {

    Long getAppUserId();

    Long getProfessionalId();

    List<String> getRoleNames();
}
