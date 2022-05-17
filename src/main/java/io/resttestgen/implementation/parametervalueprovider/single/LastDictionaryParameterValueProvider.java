package io.resttestgen.implementation.parametervalueprovider.single;

import io.resttestgen.core.Environment;
import io.resttestgen.core.datatype.parameter.ParameterLeaf;
import io.resttestgen.core.dictionary.Dictionary;
import io.resttestgen.core.dictionary.DictionaryEntry;
import io.resttestgen.core.testing.parametervalueprovider.CountableParameterValueProvider;

import java.util.List;

public class LastDictionaryParameterValueProvider implements CountableParameterValueProvider {

    // Get values from global dictionary by default
    private Dictionary dictionary = Environment.getInstance().getGlobalDictionary();

    @Override
    public int countAvailableValuesFor(ParameterLeaf parameterLeaf) {
        return parameterLeaf.countValuesInDictionary(dictionary);
    }

    @Override
    public Object provideValueFor(ParameterLeaf parameterLeaf) {
        List<DictionaryEntry> entries = dictionary
                .getEntriesByParameterName(parameterLeaf.getName(), parameterLeaf.getType());
        if (entries.size() > 0) {
            DictionaryEntry lastEntry = entries.get(0);
            for (DictionaryEntry entry : entries) {
                if (entry.getDiscoveryTime().after(lastEntry.getDiscoveryTime())) {
                    lastEntry = entry;
                }
            }
            return lastEntry;
        }
        return null;
    }

    /**
     * Set the dictionary from which the provider picks the value.
     * @param dictionary the dictionary from which the provider picks the value.
     */
    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }
}
