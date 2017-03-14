package nl.qnh.usermanagement.web.resource;

import org.hawaiiframework.web.resource.ResourceAssembler;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * {@inheritDoc}
 */
@Component
public class UUIDResourceAssembler implements ResourceAssembler<UUID, String> {

    /**
     * {@inheritDoc}
     */
    @Override
    public String toResource(final UUID object) {
        return object.toString();
    }
}
