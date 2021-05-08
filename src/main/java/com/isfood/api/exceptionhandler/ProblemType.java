package com.isfood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	ERRO_OF_SYSTEM("/erro_of_system","Erro Of System"),
	ARGUMENT_TYPE_MISMATCH("/argument_type_mismatch", "Argument Type Mismatch"),
	PROPERTY_NOT_EXSISTS("/property-not-exists", "Property Not Exsists"),
	PROPERTY_NOT_ALLOWED("/property-not-allowed", "Property Not Allowed"),
    MESSAGE_NOT_READABLE("/message-not-readable", "Menssage Not Readable"),
    RECURSE_NOT_FOUND("/recurse-not-found", "Recurse Not Found"),
    ENTITY_IN_USE("/entity-in-use", "Entity In Use"),
    CONTROLLER_EXCEPTION("/error-controller", "Controller rule violation");

    private String title;
    private String uri;

    ProblemType(String path, String title){
        this.uri = "https://isfood.com" + path;
        this.title = title;
    }
}

