
import java.nio.file.spi.FileSystemProvider;
import org.drombler.commons.iso9660fs.impl.ISOFileSystemProvider;

/**
 * NIO.2 File API provider for ISO 9660 files.
 */
module org.drombler.commons.iso9660fs {
    exports org.drombler.commons.iso9660fs;

    provides FileSystemProvider with ISOFileSystemProvider;
}
