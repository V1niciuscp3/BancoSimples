package com.example.BancoSimples.Model.Util;

import br.com.caelum.stella.format.CPFFormatter;
import br.com.caelum.stella.validation.CPFValidator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class CpfUtil {

    private final  String cpf;
    private final String cpfFormatado;

    public CpfUtil(String cpf){
        CPFFormatter formatador = new CPFFormatter();
        if (formatador.isFormatted(cpf)){
            this.cpf = formatador.unformat(cpf);
            this.cpfFormatado = cpf;
        } else if (formatador.canBeFormatted(cpf)){
            this.cpf = cpf;
            this.cpfFormatado = formatador.format(cpf);
        } else {
            this.cpf = this.cpfFormatado = cpf;
        }
    }

    public boolean isValido(){
        return new CPFValidator().invalidMessagesFor(cpf).isEmpty();
    }

    @Override
    public String toString() {
        return getCpfFormatado();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CpfUtil other = (CpfUtil) obj;
        if (cpf == null) {
            if (other.cpf != null) {
                return false;
            }
        } else if (!cpf.equals(other.cpf)) {
            return false;
        }
        return true;
    }

}
