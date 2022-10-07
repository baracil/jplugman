import jplugman.tools.FolderWatcher;

module jplugman.tools {
    uses FolderWatcher.Factory;
    requires static lombok;

    requires org.slf4j;

    exports jplugman.tools;
}
