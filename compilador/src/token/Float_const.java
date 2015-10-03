/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package token;

import tag.Tag;

/**
 *
 * @author luiz
 */
public class Float_const extends Token {

    public final float value;

    public Float_const(float value) {
        super(Tag.FLOAT_CONST);
        this.value = value;
    }

    @Override
    public String toString() {
        return "<(" + Tag.FLOAT_CONST + "),(" + value + ")>";
    }

}
