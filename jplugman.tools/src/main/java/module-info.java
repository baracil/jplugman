import jplugman.tools.FolderWatcher;

module jplugman.tools {
    uses FolderWatcher.Factory;
    requires static lombok;

    requires com.google.common;

    requires org.apache.logging.log4j;

    exports jplugman.tools;
}
