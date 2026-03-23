package eu.jacobsjo.debugPropertiesPlus.fileFix;

import com.mojang.datafixers.schemas.Schema;
import net.minecraft.util.filefix.FileFix;
import net.minecraft.util.filefix.operations.FileFixOperations;

public class WorldStorageFileFix extends FileFix {
    public WorldStorageFileFix(Schema schema) {
        super(schema);
    }

    @Override
    public void makeFixer() {
        addFileFixOperation(FileFixOperations.move("data/debug-properties-plus.dat", "data/debug-properties-plus/debug-properties.dat"));
    }
}
