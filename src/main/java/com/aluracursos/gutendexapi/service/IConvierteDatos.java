package com.aluracursos.gutendexapi.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String Json,Class<T> clase);
}
