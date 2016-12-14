package com.gatar.Spizarka.Database;

/**
 * Interface for {@link ManagerDAO} providing for outer class only one method.
 */
interface DatabaseSynchronizer{

    /**
     * Start synchronizing databases basing on database versions.
     * If versions are different, database from cloud are downloading and replace internal database.
     */
    void synchronize();
}