
import java.nio.file.spi.FileSystemProvider;
import org.drombler.commons.iso9660.impl.ISOFileSystemProvider;

/**
 * NIO.2 File API provider for ISO 9660 files.
 */
module org.drombler.commons.iso9660 {
    exports org.drombler.commons.iso9660;

    provides FileSystemProvider with ISOFileSystemProvider;
}
