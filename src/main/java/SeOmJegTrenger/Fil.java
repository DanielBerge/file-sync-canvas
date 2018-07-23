package SeOmJegTrenger;

public class Fil
{
    private String contenttype;

    private String unlock_at;

    private String url;

    private String locked_for_user;

    private String size;

    private String id;

    private String folder_id;

    private String display_name;

    private String thumbnail_url;

    private String updated_at;

    private String hidden_for_user;

    private String lock_at;

    private String hidden;

    private String filename;

    private String created_at;

    private String mime_class;

    private String uuid;

    private String locked;

    private String media_entry_id;

    private String modified_at;

    public String getContenttype ()
{
    return contenttype;
}

    public void setContenttype (String contenttype)
{
    this.contenttype = contenttype;
}

    public String getUnlock_at ()
{
    return unlock_at;
}

    public void setUnlock_at (String unlock_at)
    {
        this.unlock_at = unlock_at;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    public String getLocked_for_user ()
    {
        return locked_for_user;
    }

    public void setLocked_for_user (String locked_for_user)
    {
        this.locked_for_user = locked_for_user;
    }

    public String getSize ()
    {
        return size;
    }

    public void setSize (String size)
    {
        this.size = size;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getFolder_id ()
    {
        return folder_id;
    }

    public void setFolder_id (String folder_id)
    {
        this.folder_id = folder_id;
    }

    public String getDisplay_name ()
    {
        return display_name;
    }

    public void setDisplay_name (String display_name)
    {
        this.display_name = display_name;
    }

    public String getThumbnail_url ()
{
    return thumbnail_url;
}

    public void setThumbnail_url (String thumbnail_url)
    {
        this.thumbnail_url = thumbnail_url;
    }

    public String getUpdated_at ()
    {
        return updated_at;
    }

    public void setUpdated_at (String updated_at)
    {
        this.updated_at = updated_at;
    }

    public String getHidden_for_user ()
    {
        return hidden_for_user;
    }

    public void setHidden_for_user (String hidden_for_user)
    {
        this.hidden_for_user = hidden_for_user;
    }

    public String getLock_at ()
{
    return lock_at;
}

    public void setLock_at (String lock_at)
    {
        this.lock_at = lock_at;
    }

    public String getHidden ()
    {
        return hidden;
    }

    public void setHidden (String hidden)
    {
        this.hidden = hidden;
    }

    public String getFilename ()
    {
        return filename;
    }

    public void setFilename (String filename)
    {
        this.filename = filename;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public void setCreated_at (String created_at)
    {
        this.created_at = created_at;
    }

    public String getMime_class ()
    {
        return mime_class;
    }

    public void setMime_class (String mime_class)
    {
        this.mime_class = mime_class;
    }

    public String getUuid ()
    {
        return uuid;
    }

    public void setUuid (String uuid)
    {
        this.uuid = uuid;
    }

    public String getLocked ()
    {
        return locked;
    }

    public void setLocked (String locked)
    {
        this.locked = locked;
    }

    public String getMedia_entry_id ()
{
    return media_entry_id;
}

    public void setMedia_entry_id (String media_entry_id)
    {
        this.media_entry_id = media_entry_id;
    }

    public String getModified_at ()
    {
        return modified_at;
    }

    public void setModified_at (String modified_at)
    {
        this.modified_at = modified_at;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [contenttype = "+contenttype+", unlock_at = "+unlock_at+", url = "+url+", locked_for_user = "+locked_for_user+", size = "+size+", id = "+id+", folder_id = "+folder_id+", display_name = "+display_name+", thumbnail_url = "+thumbnail_url+", updated_at = "+updated_at+", hidden_for_user = "+hidden_for_user+", lock_at = "+lock_at+", hidden = "+hidden+", filename = "+filename+", created_at = "+created_at+", mime_class = "+mime_class+", uuid = "+uuid+", locked = "+locked+", media_entry_id = "+media_entry_id+", modified_at = "+modified_at+"]";
    }
}