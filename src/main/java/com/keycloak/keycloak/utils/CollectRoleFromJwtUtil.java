package com.keycloak.keycloak.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.log.LogMessage;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

@Component
public class CollectRoleFromJwtUtil implements Converter<Jwt, Collection<GrantedAuthority>> {

    private String authorityPrefix = "ROLE_";
    private final Log logger = LogFactory.getLog(this.getClass());
    private Collection<String> authoritiesClaimNames;
    private String authoritiesClaimDelimiter = " ";
    private static final Collection<String> WELL_KNOWN_AUTHORITIES_CLAIM_NAMES = Arrays.asList("realm_access");



    public CollectRoleFromJwtUtil() {
        this.authoritiesClaimNames = WELL_KNOWN_AUTHORITIES_CLAIM_NAMES;
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList();

        for(String authority : this.getAuthorities(jwt)) {
            grantedAuthorities.add(new SimpleGrantedAuthority(this.authorityPrefix + authority));
        }

        return grantedAuthorities;
    }

    private Collection<String> getAuthorities(Jwt jwt) {
        String claimName = this.getAuthoritiesClaimName(jwt);
        if (claimName == null) {
            this.logger.trace("Returning no authorities since could not find any claims that might contain scopes");
            return Collections.emptyList();
        } else {
            if (this.logger.isTraceEnabled()) {
                this.logger.trace(LogMessage.format("Looking for scopes in claim %s", claimName));
            }

            Object authorities = jwt.getClaim(claimName);
            if (authorities instanceof Map){
                Map<String, Object> realMap = ((Map<String, Object>) authorities);
                if(realMap.containsKey("roles")){
                    return (Collection<String>) realMap.get("roles");
                }
            }

            if (authorities instanceof String) {
                return StringUtils.hasText((String)authorities) ? Arrays.asList(((String)authorities).split(this.authoritiesClaimDelimiter)) : Collections.emptyList();
            } else {
                return (Collection<String>)(authorities instanceof Collection ? this.castAuthoritiesToCollection(authorities) : Collections.emptyList());
            }
        }
    }

    private String getAuthoritiesClaimName(Jwt jwt) {
        for(String claimName : this.authoritiesClaimNames) {
            if (jwt.hasClaim(claimName)) {
                return claimName;
            }
        }

        return null;
    }

    private Collection<String> castAuthoritiesToCollection(Object authorities) {
        return (Collection)authorities;
    }
}
