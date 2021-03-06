/*
 * Copyright 2010-2012 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.jet.plugin.formatter;

import com.intellij.application.options.IndentOptionsEditor;
import com.intellij.application.options.SmartIndentOptionsEditor;
import com.intellij.lang.Language;
import com.intellij.psi.codeStyle.CodeStyleSettingsCustomizable;
import com.intellij.psi.codeStyle.CommonCodeStyleSettings;
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jet.plugin.JetLanguage;

/**
 * @author Nikolay Krasko
 */
public class JetLanguageCodeStyleSettingsProvider extends LanguageCodeStyleSettingsProvider {
    @NotNull
    @Override
    public Language getLanguage() {
        return JetLanguage.INSTANCE;
    }

    @Override
    public String getCodeSample(@NotNull SettingsType settingsType) {
        switch (settingsType) {
            case WRAPPING_AND_BRACES_SETTINGS:
                return
                        "public class ThisIsASampleClass {\n" +
                        "    private fun foo1(i1: Int,\n" +
                        "                     i2: Int,\n" +
                        "                     i3: Int) : Int {\n" +
                        "        return 0\n" +
                        "    }\n" +
                        "    private fun foo2():Int {\n" +
                        "        return foo1(12,\n" +
                        "                13,\n" +
                        "                14\n" +
                        "        )\n" +
                        "    }\n" +
                        "}";
            default:
                return
                        "class Some {\n" +
                        "  fun foo() : Int {\n" +
                        "    val test : Int = 12\n" +
                        "    return test\n" +
                        "  }\n" +
                        "  private fun <T>foo2():Int where T : List<T> {\n" +
                        "    return 0\n" +
                        "  }\n" +
                        "}";
        }
    }

    @Override
    public String getLanguageName() {
        return JetLanguage.NAME;
    }

    @Override
    public void customizeSettings(@NotNull CodeStyleSettingsCustomizable consumer, @NotNull SettingsType settingsType) {
        switch (settingsType) {
            case SPACING_SETTINGS:
                consumer.showStandardOptions(
                        "SPACE_AROUND_ASSIGNMENT_OPERATORS",
                        "SPACE_AROUND_LOGICAL_OPERATORS",
                        "SPACE_AROUND_EQUALITY_OPERATORS",
                        "SPACE_AROUND_RELATIONAL_OPERATORS",
                        "SPACE_AROUND_ADDITIVE_OPERATORS",
                        "SPACE_AROUND_MULTIPLICATIVE_OPERATORS",
                        "SPACE_AROUND_UNARY_OPERATOR",
                        "SPACE_AFTER_COMMA",
                        "SPACE_BEFORE_COMMA"
                );

                consumer.showCustomOption(JetCodeStyleSettings.class, "SPACE_AROUND_RANGE", "Around range (..)",
                                          CodeStyleSettingsCustomizable.SPACES_AROUND_OPERATORS);

                consumer.showCustomOption(JetCodeStyleSettings.class, "SPACE_AFTER_TYPE_COLON", "Space after colon, before declarations' type",
                                          CodeStyleSettingsCustomizable.SPACES_OTHER);

                consumer.showCustomOption(JetCodeStyleSettings.class, "SPACE_BEFORE_TYPE_COLON", "Space before colon, after declarations' name",
                                          CodeStyleSettingsCustomizable.SPACES_OTHER);

                consumer.showCustomOption(JetCodeStyleSettings.class, "SPACE_AFTER_EXTEND_COLON",
                                          "Space after colon in new type definition",
                                          CodeStyleSettingsCustomizable.SPACES_OTHER);

                consumer.showCustomOption(JetCodeStyleSettings.class, "SPACE_BEFORE_EXTEND_COLON",
                                          "Space before colon in new type definition",
                                          CodeStyleSettingsCustomizable.SPACES_OTHER);
                break;
            case WRAPPING_AND_BRACES_SETTINGS:
                consumer.showStandardOptions(
                        // "ALIGN_MULTILINE_CHAINED_METHODS",
                        "ALIGN_MULTILINE_PARAMETERS",
                        "ALIGN_MULTILINE_PARAMETERS_IN_CALLS",
                        "ALIGN_MULTILINE_METHOD_BRACKETS"
                );
                break;
            default:
                consumer.showStandardOptions();
        }
    }

    @Override
    public IndentOptionsEditor getIndentOptionsEditor() {
        return new SmartIndentOptionsEditor();
    }

    @Override
    public boolean usesSharedPreview() {
        return false;
    }

    @Override
    public CommonCodeStyleSettings getDefaultCommonSettings() {
        CommonCodeStyleSettings commonCodeStyleSettings = new CommonCodeStyleSettings(getLanguage());
        commonCodeStyleSettings.initIndentOptions();
        return commonCodeStyleSettings;
    }
}
