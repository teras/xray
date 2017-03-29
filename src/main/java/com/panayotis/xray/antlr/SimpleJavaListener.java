// Generated from SimpleJava.g4 by ANTLR 4.5.3
package com.panayotis.xray.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SimpleJavaParser}.
 */
public interface SimpleJavaListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SimpleJavaParser#commandline}.
	 * @param ctx the parse tree
	 */
	void enterCommandline(SimpleJavaParser.CommandlineContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleJavaParser#commandline}.
	 * @param ctx the parse tree
	 */
	void exitCommandline(SimpleJavaParser.CommandlineContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleJavaParser#command}.
	 * @param ctx the parse tree
	 */
	void enterCommand(SimpleJavaParser.CommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleJavaParser#command}.
	 * @param ctx the parse tree
	 */
	void exitCommand(SimpleJavaParser.CommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(SimpleJavaParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(SimpleJavaParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleJavaParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionList(SimpleJavaParser.ExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleJavaParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionList(SimpleJavaParser.ExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleJavaParser#creator}.
	 * @param ctx the parse tree
	 */
	void enterCreator(SimpleJavaParser.CreatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleJavaParser#creator}.
	 * @param ctx the parse tree
	 */
	void exitCreator(SimpleJavaParser.CreatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleJavaParser#createdName}.
	 * @param ctx the parse tree
	 */
	void enterCreatedName(SimpleJavaParser.CreatedNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleJavaParser#createdName}.
	 * @param ctx the parse tree
	 */
	void exitCreatedName(SimpleJavaParser.CreatedNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleJavaParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(SimpleJavaParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleJavaParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(SimpleJavaParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleJavaParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void enterPrimitiveType(SimpleJavaParser.PrimitiveTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleJavaParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void exitPrimitiveType(SimpleJavaParser.PrimitiveTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleJavaParser#classOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void enterClassOrInterfaceType(SimpleJavaParser.ClassOrInterfaceTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleJavaParser#classOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void exitClassOrInterfaceType(SimpleJavaParser.ClassOrInterfaceTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleJavaParser#classCreatorRest}.
	 * @param ctx the parse tree
	 */
	void enterClassCreatorRest(SimpleJavaParser.ClassCreatorRestContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleJavaParser#classCreatorRest}.
	 * @param ctx the parse tree
	 */
	void exitClassCreatorRest(SimpleJavaParser.ClassCreatorRestContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleJavaParser#arguments}.
	 * @param ctx the parse tree
	 */
	void enterArguments(SimpleJavaParser.ArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleJavaParser#arguments}.
	 * @param ctx the parse tree
	 */
	void exitArguments(SimpleJavaParser.ArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleJavaParser#arrayInitializer}.
	 * @param ctx the parse tree
	 */
	void enterArrayInitializer(SimpleJavaParser.ArrayInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleJavaParser#arrayInitializer}.
	 * @param ctx the parse tree
	 */
	void exitArrayInitializer(SimpleJavaParser.ArrayInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleJavaParser#arrayCreatorRest}.
	 * @param ctx the parse tree
	 */
	void enterArrayCreatorRest(SimpleJavaParser.ArrayCreatorRestContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleJavaParser#arrayCreatorRest}.
	 * @param ctx the parse tree
	 */
	void exitArrayCreatorRest(SimpleJavaParser.ArrayCreatorRestContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleJavaParser#variableInitializer}.
	 * @param ctx the parse tree
	 */
	void enterVariableInitializer(SimpleJavaParser.VariableInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleJavaParser#variableInitializer}.
	 * @param ctx the parse tree
	 */
	void exitVariableInitializer(SimpleJavaParser.VariableInitializerContext ctx);
}