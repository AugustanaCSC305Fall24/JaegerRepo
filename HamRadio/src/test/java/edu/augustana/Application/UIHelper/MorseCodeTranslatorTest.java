package edu.augustana.Application.UIHelper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MorseCodeTranslatorTest {

    @Test
    void morseToText() {
        //Simple Morse
        assertEquals("SOS", MorseCodeTranslator.morseToText("... --- ..."));

        //Morse with space
        assertEquals("S O S", MorseCodeTranslator.morseToText("... / --- / ..."));

        //Edge Cases - Empty String
        assertEquals("", MorseCodeTranslator.morseToText(""));

        //Edge Cases - Invalid Syntax
        assertEquals("###", MorseCodeTranslator.morseToText(".....- ------ ...-.-.-."));

        //Full sentence
        assertEquals("HELLO MY NAME IS HOANG",
                MorseCodeTranslator.morseToText(".... . .-.. .-.. --- / -- -.-- / -. .- -- . / .. ... / .... --- .- -. --."));
    }

    @Test
    void textToMorse() {
        assertEquals("... --- ...", MorseCodeTranslator.textToMorse("SOS"));

        assertEquals("... / --- / ...", MorseCodeTranslator.textToMorse("S O S"));

        assertEquals("", MorseCodeTranslator.textToMorse(""));

        assertEquals(".....- ------ ...-.-.-.", MorseCodeTranslator.textToMorse("###"));

        assertEquals(".... . .-.. .-.. --- / -- -.-- / -. .- -- . / .. ... / .... --- .- -. --.",
                MorseCodeTranslator.textToMorse("HELLO MY NAME IS HOANG"));
    }
}