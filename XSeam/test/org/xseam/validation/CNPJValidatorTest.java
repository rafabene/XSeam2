package org.xseam.validation;

import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;
import org.testng.annotations.Test;

public class CNPJValidatorTest{
    
    private Pessoa p = new Pessoa();

    @Test
    public void valorValidoMascara() throws Exception {
        p.setCnpj("60.409.075/0060-02");
        ClassValidator<Pessoa> cv = new ClassValidator<Pessoa>(Pessoa.class);
        InvalidValue[] erros = cv.getInvalidValues(p);
        assert erros.length == 0 : "CNPJ [60.409.075/0060-02] would be valid!";
    }

    @Test
    public void valorValidoSemMascara() throws Exception {
        p.setCnpj("60409075006002");
        ClassValidator<Pessoa> cv = new ClassValidator<Pessoa>(Pessoa.class);
        InvalidValue[] erros = cv.getInvalidValues(p);
        assert erros.length == 0 : "CNPJ [60409075006002] would be valid!";
    }

    @Test
    public void valorInvalido() throws Exception {
        p.setCnpj("60.409.075/0060-01");
        ClassValidator<Pessoa> cv = new ClassValidator<Pessoa>(Pessoa.class);
        InvalidValue[] erros = cv.getInvalidValues(p);
        assert erros.length == 1 : "CNPJ [60.409.075/0060-01] would be invalid";
    }
    
    private static class Pessoa{
        
        @CNPJ
        private String cnpj;
        
        public void setCnpj(String cnpj) {
            this.cnpj = cnpj;
        }
        
        @SuppressWarnings("unused")
		public String getCnpj() {
            return cnpj;
        }
        
    }
}
