package com.fjrh.victorypay.dataBases.register;

import com.fjrh.victorypay.Libraries.CodeGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Register {

    public Register(){
        this.register_code = CodeGenerator.getNewCode('R');

        Date fecha = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String fechaFormateada = formato.format(fecha);
        this.date = fechaFormateada;

    }

    public Register(String user, String description, String type, String insertion_query, String rollback_query ){
        this();
        this.user = user;
        this.description = description;
        this.type = type;
        this.insertion_query = insertion_query;
        this.rollback_query = rollback_query;

        /**
         * @param user el usuario que hace el registro
         * @param description descripción del registro
         * @param type el tipo de registro
         * @param insertion_query la cadena de inserción
         * @param rollback_query la cadena para regresar los cambios
         */
    }


    private String register_code;
    private String user;
    private String description;
    private String date;
    private String type;
    private String insertion_query;
    private String rollback_query;


    public String getRegister_code() {
        return register_code;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInsertion_query() {
        return insertion_query;
    }

    public void setInsertion_query(String insertion_query) {
        this.insertion_query = insertion_query;
    }

    public String getRollback_query() {
        return rollback_query;
    }

    public void setRollback_query(String rollback_query) {
        this.rollback_query = rollback_query;
    }
}
