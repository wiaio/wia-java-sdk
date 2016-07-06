package io.wia.model;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class WiaDeletedObject {
    String id;
    Boolean deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getDeleted() { return deleted; }

    public void setDeleted(Boolean deleted) { this.deleted = deleted; }
}
