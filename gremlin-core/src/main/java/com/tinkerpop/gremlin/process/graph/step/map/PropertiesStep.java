package com.tinkerpop.gremlin.process.graph.step.map;

import com.tinkerpop.gremlin.process.Traversal;
import com.tinkerpop.gremlin.process.graph.marker.Reversible;
import com.tinkerpop.gremlin.process.util.TraversalHelper;
import com.tinkerpop.gremlin.structure.Element;
import com.tinkerpop.gremlin.structure.Property;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class PropertiesStep<E> extends FlatMapStep<Element, Property<E>> implements Reversible {

    protected final String[] propertyKeys;
    protected final boolean hidden;

    public PropertiesStep(final Traversal traversal, final boolean hidden, final String... propertyKeys) {
        super(traversal);
        this.propertyKeys = propertyKeys;
        this.hidden = hidden;
        this.setFunction(traverser -> this.hidden ?
                (Iterator) traverser.get().iterators().hiddenPropertyIterator(this.propertyKeys) :
                (Iterator) traverser.get().iterators().propertyIterator(this.propertyKeys));
    }

    @Override
    public void reverse() {
        TraversalHelper.replaceStep(this, new PropertyElementStep(this.traversal), this.traversal);
    }

    @Override
    public String toString() {
        return this.propertyKeys.length == 0 ? super.toString() : TraversalHelper.makeStepString(this, Arrays.toString(this.propertyKeys));
    }
}
