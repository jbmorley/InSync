
// package uk.co.in7.insync.sources.SyncItem;

class InSyncItem {

    // int for storing the id of the item in question.
    // It is possible that something larger than an integer would
    // be required for storing this.
    int syncItemId;

    // Items should have some way of storing which principal last saw them.
    // It would probably be sufficient to do this within comma separated list?
    // That said, we could see, from the revision history of the device (if
    // we decided to log this) those devices which had last seen that version
    // of the item?

    // Accessor Methods.

    public int getSyncItemId() {
	return syncItemId;
    }

    public void setSyncItemId( int newSyncItemId ) {
	syncItemId = newSyncItemId;
    }

}
