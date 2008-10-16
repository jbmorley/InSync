/*
 * SyncSourceHandler.java
 *
 * Copyright 2004, Jason Barrie Morley
 *
 * Provides a basic framework for additing and removing synchronisation
 * items.
 *
 */

import uk.co.in7.insync.InSyncItem;

class abstract InSyncSourceHandler {
    
    // Constructors.

    /* Accessors. */

    // Return the SyncItem with the UID, itemId. 
    public InSyncItem getSyncItem( int itemId ) {}
    // Update the syncItem.
    public void setSyncItem( SyncItem newSyncItem ) {}

    // Return the updated items.
    public InSyncItem[] getUpdatedItems( Principal principal, Timestamp since ) {}

    // Return all currently active items.
    public InSyncItem[] getActiveItems() {}

    // Return all deleted items.
    public InSyncItem[] getDeletedItems() {}
    // We need to run some sort of garbage collection on the databases to
    // check to see if we can delete itmes.  It would seem adequate
    // to run the garbage collection when we perform a synchronization.

    public purgeDeletedItems() {}

    // Return the 

    // Return only the updated items that relate to a particular item and
    // a date...
    // Errrm is it not the case that this should infact be inherited from
    // the SyncSource object?  Maybe this object should inherit 

    // Private Functions.    

}
