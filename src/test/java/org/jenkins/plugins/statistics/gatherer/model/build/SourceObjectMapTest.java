package org.jenkins.plugins.statistics.gatherer.model.build;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SourceObjectMapTest {

    public static class PrimitiveFieldsClass {
        private final String stringField;
        private final int intField;
        private final boolean boolField;


        public PrimitiveFieldsClass(String stringField, int intField, boolean boolField) {
            this.stringField = stringField;
            this.intField = intField;
            this.boolField = boolField;
        }
    }

    @Test
    public void primitiveFieldsShouldBeConvertedIntoFlatMap() {
        PrimitiveFieldsClass objWithPrimitiveFields = new PrimitiveFieldsClass("fooString", 123, true);

        SourceObjectMap objMap = SourceObjectMap.newSourceObjectMap(objWithPrimitiveFields);

        assertThat(objMap.keySet(), containsInAnyOrder("stringField", "intField", "boolField"));
    }

    public static class NestedFieldsClass {
        private final PrimitiveFieldsClass objectField;

        public NestedFieldsClass(PrimitiveFieldsClass objectField) {
            this.objectField = objectField;
        }
    }

    @Test
    public void nestedObjectShouldBeConvertedInfoNestedMap() {
        NestedFieldsClass objWithNesting = new NestedFieldsClass(new PrimitiveFieldsClass("bar", 1, true));

        SourceObjectMap objMap = SourceObjectMap.newSourceObjectMap(objWithNesting);

        assertThat(objMap.keySet(), containsInAnyOrder("objectField"));
        assertThat(objMap.get("objectField"), is(instanceOf(SourceObjectMap.class)));
    }

    public static class ListFieldsClass {
        private final List<String> stringList;

        public ListFieldsClass(List<String> stringList) {
            this.stringList = stringList;
        }
    }

    @Test
    public void listFieldShouldBeConvertedInfoNestedList() {
        ListFieldsClass objectWithList = new ListFieldsClass(Arrays.asList("foo", "bar"));

        SourceObjectMap objMap = SourceObjectMap.newSourceObjectMap(objectWithList);

        assertThat(objMap.keySet(), contains("stringList"));
        assertThat(objMap.get("stringList"), is(instanceOf(List.class)));
        assertThat((List<String>) objMap.get("stringList"), contains("foo", "bar"));
    }
}
