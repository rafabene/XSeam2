package org.xseam.validation;

import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;
import org.testng.annotations.Test;

public class PhoneValidatorTest{
    
    private Pessoa p = new Pessoa();

    @Test
    public void valorValido() throws Exception {
        p.setTelefone("(62)3324-1266");
        ClassValidator<Pessoa> cv = new ClassValidator<Pessoa>(Pessoa.class);
        InvalidValue[] erros = cv.getInvalidValues(p);
        assert erros.length == 0 : "Phone Number [(62)3324-1266] would be valid";
    }

    @Test
    public void valorInvalido() throws Exception {
        p.setTelefone("123");
        ClassValidator<Pessoa> cv = new ClassValidator<Pessoa>(Pessoa.class);
        InvalidValue[] erros = cv.getInvalidValues(p);
        assert erros.length == 1 : "Phone Number [123] would be invalid";
    }
    
    private static class Pessoa{
        
        @Phone(regexPattern="\\(\\d{2}\\)\\d{4}-\\d{4}")
        private String telefone;
        
        @SuppressWarnings("unused")
		public String getTelefone() {
            return telefone;
        }
        
        public void setTelefone(String telefone) {
            this.telefone = telefone;
        }
    }
    
}
