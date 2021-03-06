package com.github.czyzby.lml.parser.impl.tag.actor.provider;

import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.parser.impl.tag.actor.ImageTextButtonLmlTag;
import com.github.czyzby.lml.parser.tag.LmlTag;
import com.github.czyzby.lml.parser.tag.LmlTagProvider;

/** Provides ImageTextButton tags.
 *
 * @author MJ */
public class ImageTextButtonLmlTagProvider implements LmlTagProvider {
    @Override
    public LmlTag create(final LmlParser parser, final LmlTag parentTag, final StringBuilder rawTagData) {
        return new ImageTextButtonLmlTag(parser, parentTag, rawTagData);
    }
}
