/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.utils;

/**
 *
 * @author teras
 */
public interface QuadFunction<A, B, C, D, R> {

    R apply(A a, B b, C c, D d);

}
