package com.alex.loaders.items;

import java.util.Arrays;
import java.util.HashMap;

import com.alex.io.InputStream;
import com.alex.io.OutputStream;
import com.alex.store.Store;
import com.editor.Main;

@SuppressWarnings("unused")
public class ItemDefinitions implements Cloneable {

    public int id;
    private boolean loaded;
    public int modelId;
    private String name;
    // model size information
    private int spriteScale;
    private int spritePitch;
    private int spriteCameraRoll;
    private int spriteTranslateX;
    private int spriteTranslateY;
    public boolean aBool130;
    // extra information
    private int stackable;
    private int value;
    public boolean membersObject;
    // wearing model information
    public int primaryMaleModel;
    public int primaryFemaleModel;
    public int secondaryMaleModel;
    public int secondaryFemaleModel;
    public int tertiaryMaleEquipmentModel;
    public int tertiaryFemaleEquipmentModel;
    // options
    private String[] groundActions;
    public String[] itemActions;
    // model information
    public int[] originalModelColors;
    public int[] modifiedModelColors;
	public short[] originalTextureColors;
	public short[] modifiedTextureColors;
    public int[] oldoriginalModelColors;
    public int[] oldmodifiedModelColors;
	public short[] oldoriginalTextureColors;
	public short[] oldmodifiedTextureColors;
	public boolean aBool7955;
    public byte[] unknownArray1;
    public int[] unknownArray2;
    // extra information, not used for newer items
    public boolean searchable;
    public int primaryMaleHeadPiece;
    public int primaryFemaleHeadPiece;
    public int secondaryMaleHeadPiece;
    public int secondaryFemaleHeadPiece;
    public int spriteCameraYaw;
    public int unknownInt6;
    public int certID;
    public int certTemplateID;
    private int[] stackIDs;
    private int[] stackAmounts;
    public int groundScaleX;
    public int groundScaleY;
    public int groundScaleZ;
    public int ambience;
    public int diffusion;
    public int team;
    public int unknownInt12;
    public int unknownInt13;
    public int unknownInt14;
    public int unknownInt15;
    public int unknownInt16;
    public int unknownInt17;
    public int unknownInt18;
    public int unknownInt19;
    public int unknownInt20;
    public int unknownInt21;
    public int unknownInt22;
    public int unknownInt23;
    private int equipSlot;
	public int equipLookHideSlot;
	public int equipLookHideSlot2;
    public HashMap<Integer, Object> params;
    private int equipType;
    public int[] unknownArray4;
    public int[] unknownArray5;
    public byte[] unknownArray6;
    public byte[] unknownArray3;
    private boolean aBool8089;
    public int shiftClickIndex;
    public int unnotedId;
    public int notedId;

    public int placeHolderId;
    public int placeHolderTemplateId;
    private int maleTranslation;
    private int femaleTranslation;
    public int category;

    public static ItemDefinitions getItemDefinition(Store cache, int itemId) {
        return getItemDefinition(cache, itemId, true);
    }

    public static ItemDefinitions getItemDefinition(Store cache, int itemId,
            boolean load) {
        return new ItemDefinitions(cache, itemId, load);
    }


    public ItemDefinitions(Store cache, int id) {
        this(cache, id, true);
    }

    public ItemDefinitions(Store cache, int id, boolean load) {
        this.id = id;
        setDefaultsVariableValules();
        //setDefaultOptions();
        if (load) {
            loadItemDefinition(cache);
        }
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void write(Store store) {
        store.getIndexes()[2].putFile(
                getArchiveId(), getFileId(), encode());
    }

    private void loadItemDefinition(Store cache) {
        byte[] data = cache.getIndexes()[2]
                .getFile(getArchiveId(), getFileId());
        if (data == null) {
            System.out.println("FAILED LOADING ITEM " + id);
            return;
        }
        try {
            readOpcodeValues(new InputStream(data));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        if(certTemplateID != -1) {
            method4552(getItemDefinition(cache,certTemplateID), getItemDefinition(cache, certID));
        }

        if(notedId != -1) {
            method4532(getItemDefinition(cache, notedId), getItemDefinition(cache, unnotedId));
        }

        if(placeHolderTemplateId != -1) {
            method4533(getItemDefinition(cache, placeHolderTemplateId), getItemDefinition(cache, placeHolderId));
        }
        loaded = true;
    }

    private void toNote(Store store) {
        // ItemDefinitions noteItem; //certTemplateId
        ItemDefinitions realItem = getItemDefinition(store, certID);
        membersObject = realItem.membersObject;
        value = realItem.value;
        name = realItem.name;
        stackable = 1;
    }
    void method4533(ItemDefinitions var1, ItemDefinitions var2) {
        this.modelId = var1.modelId;
        this.spriteScale = var1.spriteScale;
        this.spritePitch = var1.spritePitch;
        this.spriteCameraRoll = var1.spriteCameraRoll;
        this.spriteCameraYaw = var1.spriteCameraYaw;
        this.spriteTranslateX = var1.spriteTranslateX;
        this.spriteTranslateY = var1.spriteTranslateY;
        this.originalModelColors = var1.originalModelColors;
        this.modifiedModelColors = var1.modifiedModelColors;
        this.originalTextureColors = var1.originalTextureColors;
        this.modifiedTextureColors = var1.modifiedTextureColors;
        this.stackable = var1.stackable;
        this.name = var2.name;
        this.value = 0;
        this.membersObject = false;
        this.searchable = false;
    }
    void method4552(ItemDefinitions var1, ItemDefinitions var2) {
        this.modelId = var1.modelId;
        this.spriteScale = var1.spriteScale;
        this.spritePitch = var1.spritePitch;
        this.spriteCameraRoll = var1.spriteCameraRoll;
        this.spriteCameraYaw = var1.spriteCameraYaw;
        this.spriteTranslateX = var1.spriteTranslateX;
        this.spriteTranslateY = var1.spriteTranslateY;
        this.originalModelColors = var1.originalModelColors;
        this.modifiedModelColors = var1.modifiedModelColors;
        this.originalTextureColors = var1.originalTextureColors;
        this.modifiedTextureColors = var1.modifiedTextureColors;
        this.name = var2.name;
        this.membersObject = var2.membersObject;
        this.value = var2.value;
        this.stackable = 1;
    }

    void method4532(ItemDefinitions var1, ItemDefinitions var2) {
        this.modelId = var1.modelId;
        this.spriteScale = var1.spriteScale;
        this.spritePitch = var1.spritePitch;
        this.spriteCameraRoll = var1.spriteCameraRoll;
        this.spriteCameraYaw = var1.spriteCameraYaw;
        this.spriteTranslateX = var1.spriteTranslateX;
        this.spriteTranslateY = var1.spriteTranslateY;
        this.originalModelColors = var2.originalModelColors;
        this.modifiedModelColors = var2.modifiedModelColors;
        this.originalTextureColors = var2.originalTextureColors;
        this.modifiedTextureColors = var2.modifiedTextureColors;
        this.name = var2.name;
        this.membersObject = var2.membersObject;
        this.stackable = var2.stackable;
        this.primaryMaleModel = var2.primaryMaleModel;
        this.secondaryMaleModel = var2.secondaryMaleModel;
        this.tertiaryMaleEquipmentModel = var2.tertiaryMaleEquipmentModel;
        this.primaryFemaleModel = var2.primaryFemaleModel;
        this.secondaryFemaleModel = var2.secondaryFemaleModel;
        this.tertiaryFemaleEquipmentModel = var2.tertiaryFemaleEquipmentModel;
        this.primaryMaleHeadPiece = var2.primaryMaleHeadPiece;
        this.secondaryMaleHeadPiece = var2.secondaryMaleHeadPiece;
        this.primaryFemaleHeadPiece = var2.primaryFemaleHeadPiece;
        this.secondaryFemaleHeadPiece = var2.secondaryFemaleHeadPiece;
        this.team = var2.team;
        this.groundActions = var2.groundActions;
        this.itemActions = new String[5];
        if(var2.itemActions != null) {
            for(int var3 = 0; var3 < 4; ++var3) {
                this.itemActions[var3] = var2.itemActions[var3];
            }
        }

        this.itemActions[4] = "Discard";
        this.value = 0;
    }

    public int getArchiveId() {
        return 10;
    }

    public int getFileId() {
        return id;
    }

    public boolean hasSpecialBar() {
        if (params == null) {
            return false;
        }
        Object specialBar = params.get(686);
        if (specialBar != null && specialBar instanceof Integer) {
            return (Integer) specialBar == 1;
        }
        return false;
    }

    public int getRenderAnimId() {
        if (params == null) {
            return 1426;
        }
        Object animId = params.get(644);
        if (animId != null && animId instanceof Integer) {
            return (Integer) animId;
        }
        return 1426;
    }

    public void setRenderAnimId(int animId) {
        if (params == null) {
            params = new HashMap<Integer, Object>();
        }
        params.put(644, animId);
    }

    public int getQuestId() {
        if (params == null) {
            return -1;
        }
        Object questId = params.get(861);
        if (questId != null && questId instanceof Integer) {
            return (Integer) questId;
        }
        return -1;
    }

    public HashMap<Integer, Integer> getWearingSkillRequiriments() {
        if (params == null) {
            return null;
        }
        HashMap<Integer, Integer> skills = new HashMap<Integer, Integer>();
        int nextLevel = -1;
        int nextSkill = -1;
        for (int key : params.keySet()) {
            Object value = params.get(key);
            if (value instanceof String) {
                continue;
            }
            if (key == 23) {
                skills.put(4, (Integer) value);
                skills.put(11, 61);
            } else if (key >= 749 && key < 797) {
                if (key % 2 == 0) {
                    nextLevel = (Integer) value;
                } else {
                    nextSkill = (Integer) value;
                }
                if (nextLevel != -1 && nextSkill != -1) {
                    skills.put(nextSkill, nextLevel);
                    nextLevel = -1;
                    nextSkill = -1;
                }
            }

        }
        return skills;
    }

    // test :P
    public void printClientScriptData() {
        for (int key : params.keySet()) {
            Object value = params.get(key);
            System.out.println("KEY: " + key + ", VALUE: " + value);
        }
        HashMap<Integer, Integer> requiriments = getWearingSkillRequiriments();
        if (requiriments == null) {
            System.out.println("null.");
            return;
        }
        System.out.println(requiriments.keySet().size());
        for (int key : requiriments.keySet()) {
            Object value = requiriments.get(key);
            System.out.println("SKILL: " + key + ", LEVEL: " + value);
        }
    }

    private void setDefaultOptions() {
        groundActions = new String[]{null, null, "take", null, null};
        itemActions = new String[]{null, null, null, null, "drop"};
    }

    private void setDefaultsVariableValules() {
        this.name = "null";
        this.spriteScale = 2000;
        this.spritePitch = 0;
        this.spriteCameraRoll = 0;
        this.spriteCameraYaw = 0;
        this.spriteTranslateX = 0;
        this.spriteTranslateY = 0;
        this.stackable = 0;
        this.value = 1;
        this.membersObject = false;
        this.groundActions = new String[]{null, null, "Take", null, null};
        this.itemActions = new String[]{null, null, null, null, "Drop"};
        this.shiftClickIndex = -2;
        this.primaryMaleModel = -1;
        this.secondaryMaleModel = -1;
        this.maleTranslation = 0;
        this.primaryFemaleModel = -1;
        this.secondaryFemaleModel = -1;
        this.femaleTranslation = 0;
        this.tertiaryMaleEquipmentModel = -1;
        this.tertiaryFemaleEquipmentModel = -1;
        this.primaryMaleHeadPiece = -1;
        this.secondaryMaleHeadPiece = -1;
        this.primaryFemaleHeadPiece = -1;
        this.secondaryFemaleHeadPiece = -1;
        this.certID = -1;
        this.certTemplateID = -1;
        this.groundScaleX = 128;
        this.groundScaleY = 128;
        this.groundScaleZ = 128;
        this.ambience = 0;
        this.diffusion = 0;
        this.team = 0;
        this.searchable = false;
        this.unnotedId = -1;
        this.notedId = -1;
        this.placeHolderId = -1;
        this.placeHolderTemplateId = -1;
    }
    public int unknownValue;
	public int anInt7904;
	public int anInt7923;
	public int anInt7939;
	public String aString7902;

    public byte[] encode() {
        OutputStream stream = new OutputStream();

        if (modelId != 0) {
            stream.writeByte(1);
            stream.writeShort(modelId);
        }

        if (!name.equals("null")) {
            stream.writeByte(2);
            stream.writeString(name);
        }

        if (spriteScale != 2000) {
            stream.writeByte(4);
            stream.writeShort(spriteScale);
        }

        if (spritePitch != 0) {
            stream.writeByte(5);
            stream.writeShort(spritePitch);
        }

        if (spriteCameraRoll != 0) {
            stream.writeByte(6);
            stream.writeShort(spriteCameraRoll);
        }

        if (spriteTranslateX != 0) {
            stream.writeByte(7);
            stream.writeShort(spriteTranslateX);
        }

        if (spriteTranslateY != 0) {
            stream.writeByte(8);
            stream.writeShort(spriteTranslateY);
        }

        if (stackable >= 1) {
            stream.writeByte(11);
        }

        if (value != 1) {
            stream.writeByte(12);
            stream.writeInt(value);
        }


        if (membersObject) {
            stream.writeByte(16);
        }

        if (primaryMaleModel != -1 || maleTranslation != 0) {
            stream.writeByte(23);
            stream.writeShort(primaryMaleModel);
            stream.writeByte(maleTranslation);
        }

        if (secondaryMaleModel != -1) {
            stream.writeByte(24);
            stream.writeShort(secondaryMaleModel);
        }

        if (primaryFemaleModel != -1 || femaleTranslation != -1) {
            stream.writeByte(25);
            stream.writeShort(primaryFemaleModel);
            stream.writeByte(femaleTranslation);
        }

        if (secondaryFemaleModel != -1) {
            stream.writeByte(26);
            stream.writeShort(secondaryFemaleModel);
        }

        for (int index = 0; index < groundActions.length; index++) {
            if (groundActions[index] == null
                    || (index == 2 && groundActions[index].equals("take"))) {
                continue;
            }
            stream.writeByte(30 + index);
            stream.writeString(groundActions[index]);
            stream.writeByte(10);
        }

        for (int index = 0; index < itemActions.length; index++) {
            if (itemActions[index] == null
                    || (index == 4 && itemActions[index].equals("drop"))) {
                continue;
            }
            stream.writeByte(35 + index);
            stream.writeString(itemActions[index]);
            stream.writeByte(10);
        }

        if (originalModelColors != null && modifiedModelColors != null) {
            stream.writeByte(40);
            stream.writeByte(originalModelColors.length);
            for (int index = 0; index < originalModelColors.length; index++) {
                stream.writeShort(originalModelColors[index]);
                stream.writeShort(modifiedModelColors[index]);
            }
        }

        if (originalTextureColors != null && modifiedTextureColors != null) {
            stream.writeByte(41);
            stream.writeByte(originalTextureColors.length);
            for (int index = 0; index < originalTextureColors.length; index++) {
                stream.writeShort(originalTextureColors[index]);
                stream.writeShort(modifiedTextureColors[index]);
            }
        }
        if (shiftClickIndex != -1) {
            stream.writeByte(42);
            stream.writeByte(shiftClickIndex);
        }
        if (searchable) {
            stream.writeByte(65);
        }

        if (tertiaryMaleEquipmentModel != -1) {
            stream.writeByte(78);
            stream.writeShort(tertiaryMaleEquipmentModel);
        }

        if (tertiaryFemaleEquipmentModel != -1) {
            stream.writeByte(79);
            stream.writeShort(tertiaryFemaleEquipmentModel);
        }

        if (primaryMaleHeadPiece != 0) {
            stream.writeByte(90);
            stream.writeShort(primaryMaleHeadPiece);
        }
        if (primaryFemaleHeadPiece != 0) {
            stream.writeByte(91);
            stream.writeShort(primaryFemaleHeadPiece);
        }
        if (secondaryMaleHeadPiece != 0) {
            stream.writeByte(92);
            stream.writeShort(secondaryMaleHeadPiece);
        }
        if (secondaryFemaleHeadPiece != 0) {
            stream.writeByte(93);
            stream.writeShort(secondaryFemaleHeadPiece);
        }
        if (category != -1) {
            stream.writeByte(94);
            stream.writeShort(category);
        }
        if (spriteCameraYaw != 0) {
            stream.writeByte(95);
            stream.writeShort(spriteCameraYaw);
        }
        if (unknownInt6 != 0) {
            stream.writeByte(96);
            stream.writeShort(unknownInt6);
        }

        if (certID != -1) {
            stream.writeByte(97);
            stream.writeShort(certID);
        }

        if (certTemplateID != -1) {
            stream.writeByte(98);
            stream.writeShort(certTemplateID);
        }

        if (stackIDs != null && stackAmounts != null) {
            for (int index = 0; index < stackIDs.length; index++) {
                if (stackIDs[index] == 0 && stackAmounts[index] == 0) {
                    continue;
                }
                stream.writeByte(100 + index);
                stream.writeShort(stackIDs[index]);
                stream.writeShort(stackAmounts[index]);
            }
        }
        
        if (groundScaleX != 0) {
            stream.writeByte(110);
            stream.writeShort(groundScaleX);
        }
        if (groundScaleY != 0) {
            stream.writeByte(111);
            stream.writeShort(groundScaleY);
        }
        if (groundScaleZ != 128) {
            stream.writeByte(112);
            stream.writeShort(groundScaleZ);
        }
        if (ambience != 0) {
            stream.writeByte(113);
            stream.writeByte(ambience);
        }
        if (diffusion != 0) {
            stream.writeByte(114);
            stream.writeByte(diffusion);
        }

        if (team != 0) {
            stream.writeByte(115);
            stream.writeByte(team);
        }
        if (unnotedId != -1) {
            stream.writeByte(139);
            stream.writeShort(unnotedId);
        }

        if (notedId != -1) {
            stream.writeByte(140);
            stream.writeShort(notedId);
        }

        if (placeHolderId != -1) {
            stream.writeByte(148);
            stream.writeShort(placeHolderId);
        }

        if (placeHolderTemplateId != -1) {
            stream.writeByte(149);
            stream.writeShort(placeHolderTemplateId);
        }
        if (params != null) {
            stream.writeByte(249);
            stream.writeByte(params.size());
            for (int key : params.keySet()) {
                Object value = params.get(key);
                stream.writeByte(value instanceof String ? 1 : 0);
                stream.write24BitInt(key);
                if (value instanceof String) {
                    stream.writeString((String) value);
                } else {
                    stream.writeInt((Integer) value);
                }
            }
        }
        // end
        stream.writeByte(0);

        byte[] data = new byte[stream.getOffset()];
        stream.setOffset(0);
        stream.getBytes(data, 0, data.length);
        return data;
    }
	public final void readValues(InputStream stream, int opcode) {
		if (opcode == 1)
            modelId = stream.readUnsignedShort();
		else if (opcode == 2)
			name = stream.readString();
		else if (opcode == 4)
            spriteScale = stream.readUnsignedShort();
		else if (opcode == 5)
            spritePitch = stream.readUnsignedShort();
		else if (opcode == 6)
            spriteCameraRoll = stream.readUnsignedShort();
		else if (opcode == 7) {
            spriteTranslateX = stream.readUnsignedShort();
			if (spriteTranslateX > 32767)
				spriteTranslateX -= 65536;
		} else if (opcode == 8) {
			spriteTranslateY = stream.readUnsignedShort();
			if (spriteTranslateY > 32767)
				spriteTranslateY -= 65536;
        } else if(opcode == 9) {
            stream.readString();
		} else if (opcode == 11)
			stackable = 1;
		else if (opcode == 12)
			value = stream.readInt();
		 else if (opcode == 16) {
            membersObject = true;
		} else if (opcode == 23) {
            primaryMaleModel = stream.readUnsignedShort();
            maleTranslation = stream.readUnsignedByte();
        } else if (opcode == 24) {
            secondaryMaleModel = stream.readUnsignedShort();
        } else if (opcode == 25) {
            primaryFemaleModel = stream.readUnsignedShort();
            femaleTranslation = stream.readUnsignedByte();
        } else if (opcode == 26)
            secondaryFemaleModel = stream.readUnsignedShort();
		else if (opcode >= 30 && opcode < 35)
            groundActions[opcode - 30] = stream.readString();
		else if (opcode >= 35 && opcode < 40)
            itemActions[opcode - 35] = stream.readString();
		else if (opcode == 40) {
			int length = stream.readUnsignedByte();
			originalModelColors = new int[length];
			modifiedModelColors = new int[length];
			for (int index = 0; index < length; index++) {
				originalModelColors[index] = stream.readUnsignedShort();
				modifiedModelColors[index] = stream.readUnsignedShort();
			}
		} else if (opcode == 41) {
			int length = stream.readUnsignedByte();
			originalTextureColors = new short[length];
			modifiedTextureColors = new short[length];
			for (int index = 0; index < length; index++) {
				originalTextureColors[index] = (short) stream.readUnsignedShort();
				modifiedTextureColors[index] = (short) stream.readUnsignedShort();
			}
        } else if (opcode == 42) {
            shiftClickIndex = stream.readUnsignedByte();
		} else if (opcode == 65)
            searchable = true;
		else if (opcode == 78)
            tertiaryMaleEquipmentModel = stream.readUnsignedShort();
		else if (opcode == 79)
            tertiaryFemaleEquipmentModel = stream.readUnsignedShort();
		else if (opcode == 90)
            primaryMaleHeadPiece = stream.readUnsignedShort();
		else if (opcode == 91)
            primaryFemaleHeadPiece = stream.readUnsignedShort();
		else if (opcode == 92)
            secondaryMaleHeadPiece = stream.readUnsignedShort();
		else if (opcode == 93)
            secondaryFemaleHeadPiece = stream.readUnsignedShort();
		else if (opcode == 94) {// new
            category = stream.readUnsignedShort();
		} else if (opcode == 95)
            spriteCameraYaw = stream.readUnsignedShort();
		else if (opcode == 97)
            certID = stream.readUnsignedShort();
		else if (opcode == 98)
            certTemplateID = stream.readUnsignedShort();
		else if (opcode >= 100 && opcode < 110) {
			if (stackIDs == null) {
				stackIDs = new int[10];
                stackAmounts = new int[10];
			}
			stackIDs[opcode - 100] = stream.readUnsignedShort();
			stackAmounts[opcode - 100] = stream.readUnsignedShort();
		} else if (opcode == 110)
            groundScaleX = stream.readUnsignedShort();
		else if (opcode == 111)
            groundScaleY = stream.readUnsignedShort();
		else if (opcode == 112)
            groundScaleZ = stream.readUnsignedShort();
		else if (opcode == 113)
            ambience = stream.readByte();
		else if (opcode == 114)
            diffusion = stream.readByte() * 5;
		else if (opcode == 115)
            team = stream.readUnsignedByte();
     else if (opcode == 139)
        this.unnotedId = stream.readUnsignedShort();
     else if (opcode == 140)
        this.notedId =stream.readUnsignedShort();
     else if (opcode == 148)
        this.placeHolderId = stream.readUnsignedShort();
     else if (opcode == 149)
        this.placeHolderTemplateId = stream.readUnsignedShort();
        else if(opcode == 249) {
            int size = stream.readUnsignedByte();
            if(this.params == null)
                this.params = new HashMap<>();
            for(int i = 0; i < size; i++) {
                boolean stringType = stream.readUnsignedByte() == 1;
                int key = stream.read24BitInt();
                if(stringType)
                    this.params.put(key, stream.readString());
                else
                    this.params.put(key, stream.readInt());
            }
        } else /*throw new RuntimeException("MISSING OPCODE " + opcode
         + " FOR ITEM " + id);*/ {
            Main.log("ItemDefinitions", "Missing Opcode " + opcode + " for item " + id);
        }
    }
    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public int getSpriteScale() {
        return spriteScale;
    }

    public void setSpriteScale(int modelZoom) {
        this.spriteScale = modelZoom;
    }

    private void readOpcodeValues(InputStream stream) {
        while (true) {
            int opcode = stream.readUnsignedByte();
            if (opcode == 0) {
                break;
            }
            readValues(stream, opcode);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void resetTextureColors() {
        originalTextureColors = null;
        modifiedTextureColors = null;
    }
    

    public void resetoldTextureColors() {
        oldoriginalTextureColors = null;
        oldmodifiedTextureColors = null;
    }

    public void changeTextureColor(int originalModelColor,
            int modifiedModelColor) {
        if (originalTextureColors != null) {
            for (int i = 0; i < originalTextureColors.length; i++) {
                if (originalTextureColors[i] == originalModelColor) {
                    modifiedTextureColors[i] = (short) modifiedModelColor;
                    return;
                }
            }
            short[] newOriginalModelColors = Arrays.copyOf(originalTextureColors,
                    originalTextureColors.length + 1);
            short[] newModifiedModelColors = Arrays.copyOf(modifiedTextureColors,
                    modifiedTextureColors.length + 1);
            newOriginalModelColors[newOriginalModelColors.length - 1] = (short) originalModelColor;
            newModifiedModelColors[newModifiedModelColors.length - 1] = (short) modifiedModelColor;
            originalTextureColors = newOriginalModelColors;
            modifiedTextureColors = newModifiedModelColors;
        } else {
            originalTextureColors = new short[]{(short) originalModelColor};
            modifiedTextureColors = new short[]{(short) modifiedModelColor};
        }
    }

    public void resetModelColors() {
        originalModelColors = null;
        modifiedModelColors = null;
    }
    public void resetModeloldColors() {
        oldoriginalModelColors = null;
        oldmodifiedModelColors = null;
    }

    public void changeModelColor(int originalModelColor, int modifiedModelColor) {
        if (originalModelColors != null) {
            for (int i = 0; i < originalModelColors.length; i++) {
                if (originalModelColors[i] == originalModelColor) {
                    modifiedModelColors[i] = modifiedModelColor;
                    return;
                }
            }
            int[] newOriginalModelColors = Arrays.copyOf(originalModelColors,
                    originalModelColors.length + 1);
            int[] newModifiedModelColors = Arrays.copyOf(modifiedModelColors,
                    modifiedModelColors.length + 1);
            newOriginalModelColors[newOriginalModelColors.length - 1] = originalModelColor;
            newModifiedModelColors[newModifiedModelColors.length - 1] = modifiedModelColor;
            originalModelColors = newOriginalModelColors;
            modifiedModelColors = newModifiedModelColors;
        } else {
            originalModelColors = new int[]{originalModelColor};
            modifiedModelColors = new int[]{modifiedModelColor};
        }
    }

    public String[] getGroundActions() {
        return groundActions;
    }

    public String[] getItemActions() {
        return itemActions;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSpritePitch() {
        return spritePitch;
    }

    public void setSpritePitch(int spritePitch) {
        this.spritePitch = spritePitch;
    }

    public int getSpriteCameraRoll() {
        return spriteCameraRoll;
    }

    public void setSpriteCameraRoll(int spriteCameraRoll) {
        this.spriteCameraRoll = spriteCameraRoll;
    }

    public int getSpriteTranslateX() {
        return spriteTranslateX;
    }

    public void setSpriteTranslateX(int spriteTranslateX) {
        this.spriteTranslateX = spriteTranslateX;
    }

    public int getSpriteTranslateY() {
        return spriteTranslateY;
    }

    public void setSpriteTranslateY(int spriteTranslateY) {
        this.spriteTranslateY = spriteTranslateY;
    }

    public int getStackable() {
        return stackable;
    }

    public void setStackable(int stackable) {
        this.stackable = stackable;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isMembersObject() {
        return membersObject;
    }

    public void setMembersObject(boolean membersObject) {
        this.membersObject = membersObject;
    }
    public void setaBool130(boolean membersOnly) {
        this.aBool130 = membersOnly;
    }
    public int getPrimaryMaleModel() {
        return primaryMaleModel;
    }

    public void setPrimaryMaleModel(int primaryMaleModel) {
        this.primaryMaleModel = primaryMaleModel;
    }

    public int getPrimaryFemaleModel() {
        return primaryFemaleModel;
    }

    public void setPrimaryFemaleModel(int primaryFemaleModel) {
        this.primaryFemaleModel = primaryFemaleModel;
    }

    public int getSecondaryMaleModel() {
        return secondaryMaleModel;
    }

    public void setSecondaryMaleModel(int secondaryMaleModel) {
        this.secondaryMaleModel = secondaryMaleModel;
    }

    public int getSecondaryFemaleModel() {
        return secondaryFemaleModel;
    }

    public void setSecondaryFemaleModel(int secondaryFemaleModel) {
        this.secondaryFemaleModel = secondaryFemaleModel;
    }

    public int getTertiaryMaleEquipmentModel() {
        return tertiaryMaleEquipmentModel;
    }

    public void setTertiaryMaleEquipmentModel(int tertiaryMaleEquipmentModel) {
        this.tertiaryMaleEquipmentModel = tertiaryMaleEquipmentModel;
    }

    public int getTertiaryFemaleEquipmentModel() {
        return tertiaryFemaleEquipmentModel;
    }

    public void setTertiaryFemaleEquipmentModel(int tertiaryFemaleEquipmentModel) {
        this.tertiaryFemaleEquipmentModel = tertiaryFemaleEquipmentModel;
    }

    public int[] getOriginalModelColors() {
        return originalModelColors;
    }

    public void setOriginalModelColors(int[] originalModelColors) {
        this.originalModelColors = originalModelColors;
    }

    public int[] getModifiedModelColors() {
        return modifiedModelColors;
    }

    public void setModifiedModelColors(int[] modifiedModelColors) {
        this.modifiedModelColors = modifiedModelColors;
    }

    public short[] getOriginalTextureColors() {
        return originalTextureColors;
    }

    public void setOriginalTextureColors(short[] originalTextureColors) {
        this.originalTextureColors = originalTextureColors;
    }

    public short[] getModifiedTextureColors() {
        return modifiedTextureColors;
    }

    public void setModifiedTextureColors(short[] modifiedTextureColors) {
        this.modifiedTextureColors = modifiedTextureColors;
    }

    public boolean isSearchable() {
        return searchable;
    }

    public void setSearchable(boolean searchable) {
        this.searchable = searchable;
    }

    public int getCertID() {
        return certID;
    }

    public void setCertID(int certID) {
        this.certID = certID;
    }

    public int getCertTemplateID() {
        return certTemplateID;
    }

    public void setCertTemplateID(int certTemplateID) {
        this.certTemplateID = certTemplateID;
    }

    public int[] getStackIDs() {
        return stackIDs;
    }

    public void setStackIDs(int[] stackIDs) {
        this.stackIDs = stackIDs;
    }

    public int[] getStackAmounts() {
        return stackAmounts;
    }

    public void setStackAmounts(int[] stackAmounts) {
        this.stackAmounts = stackAmounts;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public int getunNotedID() {
        return unnotedId;
    }

    public void setunNotedID(int unnotedId) {
        this.unnotedId = unnotedId;
    }

    public int getLendedItemId() {
        return notedId;
    }

    public void setLendedItemId(int lendedItemId) {
        this.notedId = lendedItemId;
    }

    public int getEquipSlot() {
        return equipSlot;
    }

    public void setEquipSlot(int equipSlot) {
        this.equipSlot = equipSlot;
    }

    public int getEquipType() {
        return equipType;
    }

    public void setEquipType(int equipType) {
        this.equipType = equipType;
    }

    public void setGroundActions(String[] groundActions) {
        this.groundActions = groundActions;
    }

    public void setItemActions(String[] itemActions) {
        this.itemActions = itemActions;
    }
}
