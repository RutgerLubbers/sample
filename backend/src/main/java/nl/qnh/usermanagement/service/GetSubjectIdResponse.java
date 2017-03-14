package nl.qnh.usermanagement.service;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

/**
 * Mirrors the response from the SSO's get subjectId call.
 */
public class GetSubjectIdResponse {

    /**
     * The subject id.
     */
    private String subjectId;

    @SuppressWarnings("PMD.CommentRequired")
    public String getSubjectId() {
        return subjectId;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public void setSubjectId(final String subjectId) {
        this.subjectId = subjectId;
    }

    /**
     * The the subjectId as UUUID.
     *
     * @return The UUID.
     */
    public UUID getUUID() {
        requireNonNull(subjectId);
        return UUID.fromString(subjectId);
    }
}
