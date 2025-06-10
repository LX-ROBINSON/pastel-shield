package org.mysterysolved.appshield.security;

import de.svws_nrw.ext.jbcrypt.BCrypt;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class BcryptPassword {

    public String generate(char[] chars) {
        return BCrypt.hashpw(new String(chars), BCrypt.gensalt(12));
    }

    public boolean verify(char[] chars, String s) {
        return BCrypt.checkpw(new String(chars), s);
    }
}
