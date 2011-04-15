package org.xseam.validation;

import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;
import org.testng.annotations.Test;

public class CPFValidatorTest{
    
    private Pessoa p = new Pessoa();

    @Test
    public void valorValidoMascara() throws Exception {
        p.setCpf("779.260.801-87");
        ClassValidator<Pessoa> cv = new ClassValidator<Pessoa>(Pessoa.class);
        InvalidValue[] erros = cv.getInvalidValues(p);
        assert erros.length == 0 : "CPF [779.260.801-87] would be valid";
    }

    @Test
    public void valorValidoSemMascara() throws Exception {
        p.setCpf("77926080187");
        ClassValidator<Pessoa> cv = new ClassValidator<Pessoa>(Pessoa.class);
        InvalidValue[] erros = cv.getInvalidValues(p);
        assert erros.length == 0 : "CPF [77926080187] would be valid";
    }

    @Test
    public void valorInvalido() throws Exception {
        p.setCpf("779.260.801-88");
        ClassValidator<Pessoa> cv = new ClassValidator<Pessoa>(Pessoa.class);
        InvalidValue[] erros = cv.getInvalidValues(p);
        assert erros.length == 1 : "CPF [779.260.801-88] would be invalid";
    }
    
    private static class Pessoa{
        
        @CPF
        private String cpf;
        
        public void setCpf(String cpf) {
            this.cpf = cpf;
        }
        
        @SuppressWarnings("unused")
		public String getCpf() {
            return cpf;
        }
        
    }
}
