/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.plugin.io;

import javax.swing.SizeRequirements;
import javax.swing.text.Element;
import javax.swing.text.View;
import static javax.swing.text.View.GoodBreakWeight;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.InlineView;
import javax.swing.text.html.ParagraphView;

/**
 *
 * http://java-sl.com/tip_html_letter_wrap.html
 */
public class LetterBasedKit extends HTMLEditorKit {

    @Override
    public ViewFactory getViewFactory() {
        return new HTMLFactory() {
            @Override
            public View create(Element e) {
                View v = super.create(e);
                if (v instanceof InlineView)
                    return new InlineView(e) {
                        @Override
                        public int getBreakWeight(int axis, float pos, float len) {
                            return GoodBreakWeight;
                        }

                        @Override
                        public View breakView(int axis, int p0, float pos, float len) {
                            if (axis == View.X_AXIS) {
                                checkPainter();
                                int p1 = getGlyphPainter().getBoundedPosition(this, p0, pos, len);
                                if (p0 == getStartOffset() && p1 == getEndOffset())
                                    return this;
                                return createFragment(p0, p1);
                            }
                            return this;
                        }
                    };
                else if (v instanceof ParagraphView)
                    return new ParagraphView(e) {
                        @Override
                        protected SizeRequirements calculateMinorAxisRequirements(int axis, SizeRequirements r) {
                            if (r == null)
                                r = new SizeRequirements();
                            float pref = layoutPool.getPreferredSpan(axis);
                            float min = layoutPool.getMinimumSpan(axis);
                            // Don't include insets, Box.getXXXSpan will include them.
                            r.minimum = (int) min;
                            r.preferred = Math.max(r.minimum, (int) pref);
                            r.maximum = Integer.MAX_VALUE;
                            r.alignment = 0.5f;
                            return r;
                        }
                    };
                return v;
            }
        };
    }
}
