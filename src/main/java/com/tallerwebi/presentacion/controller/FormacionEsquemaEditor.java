package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.model.enums.FormacionEsquema;

import java.beans.PropertyEditorSupport;

public class FormacionEsquemaEditor extends PropertyEditorSupport {
   @Override
   public void setAsText(String text) throws IllegalArgumentException {
      setValue(FormacionEsquema.fromString(text));
   }
}
