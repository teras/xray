/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.utils;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

/**
 *
 * @author teras
 */
public class CodeInjector {

    public static CtClass inject(String className, String methodName, String[] params, CtClass returnType, String body) throws NotFoundException, CannotCompileException {
        return inject(ClassPool.getDefault().get(className), methodName, params, returnType, body);
    }

    public static CtClass inject(CtClass baseclass, String methodName, String[] params, CtClass returnType, String body) throws NotFoundException, CannotCompileException {
        ClassPool classPool = ClassPool.getDefault();
        CtClass[] paramclass = new CtClass[params.length];
        for (int i = 0; i < params.length; i++)
            paramclass[i] = classPool.get(params[i]);

        try {
            baseclass.getDeclaredMethod(methodName, paramclass).insertAfter(body);
        } catch (NotFoundException ex) {
            CtMethod method = new CtMethod(returnType, methodName, paramclass, baseclass);
            method.setBody(body);
            baseclass.addMethod(method);
        }
        return baseclass;
    }
}
