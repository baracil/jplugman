import jplugman.tools.FolderWatcher;

module jplugman.tools {
    uses FolderWatcher.Factory;
    requires static lombok;

    requires org.apache.logging.log4j;

    exports jplugman.tools;
}
