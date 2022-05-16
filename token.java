public static Mono<String> getVal(final String key){
        return ReactiveSecurityContextHolder.getContext()
//                .switchIfEmpty(Mono.error(new IllegalStateException("ReactiveSecurityContext is empty")))
                .map(SecurityContext::getAuthentication)
                .map(auth-> {
                    try {
                        final Map<String, String> atts = mapper.convertValue(auth.getPrincipal(), Map.class);
                        final Map<String, String> claims = mapper.convertValue(atts.get("claims"), Map.class);
                        return claims.get(key);
                    }
                    catch(Exception e) {
                        return "-";
                    }
                })
                .onErrorResume(thro-> Mono.just("-"))
                ;
    }
