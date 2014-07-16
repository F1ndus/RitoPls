package Releasemanifest;

import LittleEndian.LeWord;
import Util.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by philipp on 16.07.2014.
 */
public class ReleaseManifestDirectoryEntry {

    private byte[] directoryFileContent;
    private long offsetEntry;
    private ReleasemanifestFile relFile;
    private long nameIndex;
    private long subDirCount;
    private long subDirFirstIndex;
    private long fileStartOffset;
    private long fileCount;
    private String name;
    private  ReleaseManifestDirectoryEntry relDirecttoryEntry;
    private List<ReleasemanifestFileEntry> relFileEntry;
    private int entryNum;

    public ReleaseManifestDirectoryEntry(ReleasemanifestFile relFile,List<Byte> content, long offset, int entryNum)
    {
        this.relFile = relFile;
        this.entryNum = entryNum;
        this.directoryFileContent = ArrayUtils.objectArrayToByteArray(content.toArray());
        this.offsetEntry = offset;
        this.nameIndex = new LeWord(content,(int)offsetEntry).getContent();
        this.subDirFirstIndex = new LeWord(content,(int)offsetEntry+4).getContent();
        this.subDirCount = new LeWord(content,(int)offsetEntry+8).getContent();
        this.fileCount = new LeWord(content,(int)offsetEntry+12).getContent();

        if(nameIndex == 0)
            this.name = "";
        else
            this.name = relFile.getStringList().getStringList().get((int)nameIndex-1);

        relFileEntry = new ArrayList<ReleasemanifestFileEntry>();
    }



    public String getFullPath() {
        if(this.relDirecttoryEntry!= null)
        {
            return this.relDirecttoryEntry.getFullPath()+"/"+name;

        }
        return name;
    }
}
