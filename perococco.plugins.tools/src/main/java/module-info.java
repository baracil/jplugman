module Bastien Aracil.plugins.tools {
    uses Bastien Aracil.plugins.tools.FolderWatcher.Factory;
    requires static lombok;

    requires com.google.common;

    requires org.apache.logging.log4j;

    exports Bastien Aracil.plugins.tools;
}
