package team.gutterteam123.netlib.packetbase.serialized;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufProcessor;
import io.netty.handler.codec.DecoderException;
import io.netty.util.ByteProcessor;
import io.netty.util.CharsetUtil;
import team.gutterteam123.netlib.packetbase.serialized.SerializedPacket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.UUID;

public class PacketSerializer extends ByteBuf {

    private final ByteBuf buf;

    public PacketSerializer(ByteBuf buf) {
        this.buf = buf;
    }

    public void writeVarInt(int value) {
        byte part;
        do {
            part = (byte) (value & 0x7F);
            value >>>= 7;
            if(value != 0)
                part |= 0x80;
            this.buf.writeByte(part);
        } while (value != 0);
    }

    public int readVarInt() {
        int out = 0, bytes = 0;
        byte part;
        do {
            part = this.buf.readByte();
            out |= (part & 0x7F) << (bytes++ * 7);
            if(bytes > 5)
                throw new DecoderException(String.format("VarInt is too long! (%d > 5)", bytes));
        } while ((part & 0x80) == 0x80);
        return out;
    }

    public <T> T readEnum(Class<T> enumClass) {
        return enumClass.getEnumConstants()[this.readVarInt()];
    }

    public void writeEnum(Enum<?> source) {
        this.writeVarInt(source.ordinal());
    }

    public UUID readUUID() {
        return new UUID(this.buf.readLong(), this.buf.readLong());
    }

    public void writeUUID(UUID uuid) {
        this.buf.writeLong(uuid.getMostSignificantBits());
        this.buf.writeLong(uuid.getLeastSignificantBits());
    }

    public SerializedPacket readPacket(SerializedPacket packet) {
        packet.read(this);
        return packet;
    }

    public void writePacket(SerializedPacket packet) {
        packet.write(this);
    }

    public String readString() {
        byte[] b = new byte[readVarInt()];
        readBytes(b);
        return new String(b, CharsetUtil.UTF_8);
    }

    public void writeString(String string) {
        byte[] bytes = string.getBytes(CharsetUtil.UTF_8);
        this.writeVarInt(bytes.length);
        this.writeBytes(bytes);
    }

    public ArrayList<String> readStringList(){
        int i = readVarInt();
        ArrayList<String> list = new ArrayList<>();
        while(i != 0) {
            list.add(readString());
            i--;
        }
        return list;
    }

    public ArrayList<Integer> readIntList(){
        int i = readVarInt();
        ArrayList<Integer> list = new ArrayList<>();
        while(i != 0) {
            list.add(readVarInt());
            i--;
        }
        return list;
    }

    public void writeIntList(ArrayList<Integer>list) {
        writeVarInt(list.size());
        for(Integer i : list)
            writeVarInt(i);
    }

    public void writeStringList(ArrayList<String>list) {
        writeVarInt(list.size());
        for(String s : list)
            writeString(s);
    }

    public int capacity() {
        return this.buf.capacity();
    }

    public ByteBuf capacity(int i) {
        return this.buf.capacity(i);
    }

    public int maxCapacity() {
        return this.buf.maxCapacity();
    }

    public ByteBufAllocator alloc() {
        return this.buf.alloc();
    }

    public ByteOrder order() {
        return this.buf.order();
    }

    public ByteBuf order(ByteOrder byteorder) {
        return this.buf.order(byteorder);
    }

    public ByteBuf unwrap() {
        return this.buf.unwrap();
    }

    public boolean isDirect() {
        return this.buf.isDirect();
    }

    public int readerIndex() {
        return this.buf.readerIndex();
    }

    public ByteBuf readerIndex(int i) {
        return this.buf.readerIndex(i);
    }

    public int writerIndex() {
        return this.buf.writerIndex();
    }

    public ByteBuf writerIndex(int i) {
        return this.buf.writerIndex(i);
    }

    public ByteBuf setIndex(int i, int j) {
        return this.buf.setIndex(i, j);
    }

    public int readableBytes() {
        return this.buf.readableBytes();
    }

    public int writableBytes() {
        return this.buf.writableBytes();
    }

    public int maxWritableBytes() {
        return this.buf.maxWritableBytes();
    }

    public boolean isReadable() {
        return this.buf.isReadable();
    }

    public boolean isReadable(int i) {
        return this.buf.isReadable(i);
    }

    public boolean isWritable() {
        return this.buf.isWritable();
    }

    public boolean isWritable(int i) {
        return this.buf.isWritable(i);
    }

    public ByteBuf clear() {
        return this.buf.clear();
    }

    public ByteBuf markReaderIndex() {
        return this.buf.markReaderIndex();
    }

    public ByteBuf resetReaderIndex() {
        return this.buf.resetReaderIndex();
    }

    public ByteBuf markWriterIndex() {
        return this.buf.markWriterIndex();
    }

    public ByteBuf resetWriterIndex() {
        return this.buf.resetWriterIndex();
    }

    public ByteBuf discardReadBytes() {
        return this.buf.discardReadBytes();
    }

    public ByteBuf discardSomeReadBytes() {
        return this.buf.discardSomeReadBytes();
    }

    public ByteBuf ensureWritable(int i) {
        return this.buf.ensureWritable(i);
    }

    public int ensureWritable(int i, boolean flag) {
        return this.buf.ensureWritable(i, flag);
    }

    public boolean getBoolean(int i) {
        return this.buf.getBoolean(i);
    }

    public byte getByte(int i) {
        return this.buf.getByte(i);
    }

    public short getUnsignedByte(int i) {
        return this.buf.getUnsignedByte(i);
    }

    public short getShort(int i) {
        return this.buf.getShort(i);
    }

    public int getUnsignedShort(int i) {
        return this.buf.getUnsignedShort(i);
    }

    public int getMedium(int i) {
        return this.buf.getMedium(i);
    }

    public int getUnsignedMedium(int i) {
        return this.buf.getUnsignedMedium(i);
    }

    public int getInt(int i) {
        return this.buf.getInt(i);
    }

    public long getUnsignedInt(int i) {
        return this.buf.getUnsignedInt(i);
    }

    public long getLong(int i) {
        return this.buf.getLong(i);
    }

    public char getChar(int i) {
        return this.buf.getChar(i);
    }

    public float getFloat(int i) {
        return this.buf.getFloat(i);
    }

    public double getDouble(int i) {
        return this.buf.getDouble(i);
    }

    public ByteBuf getBytes(int i, ByteBuf bytebuf) {
        return this.buf.getBytes(i, bytebuf);
    }

    public ByteBuf getBytes(int i, ByteBuf bytebuf, int j) {
        return this.buf.getBytes(i, bytebuf, j);
    }

    public ByteBuf getBytes(int i, ByteBuf bytebuf, int j, int k) {
        return this.buf.getBytes(i, bytebuf, j, k);
    }

    public ByteBuf getBytes(int i, byte[] abyte) {
        return this.buf.getBytes(i, abyte);
    }

    public ByteBuf getBytes(int i, byte[] abyte, int j, int k) {
        return this.buf.getBytes(i, abyte, j, k);
    }

    public ByteBuf getBytes(int i, ByteBuffer bytebuffer) {
        return this.buf.getBytes(i, bytebuffer);
    }

    public ByteBuf getBytes(int i, OutputStream outputstream, int j) throws IOException {
        return this.buf.getBytes(i, outputstream, j);
    }

    public int getBytes(int i, GatheringByteChannel gatheringbytechannel, int j) throws IOException {
        return this.buf.getBytes(i, gatheringbytechannel, j);
    }

    public ByteBuf setBoolean(int i, boolean flag) {
        return this.buf.setBoolean(i, flag);
    }

    public ByteBuf setByte(int i, int j) {
        return this.buf.setByte(i, j);
    }

    public ByteBuf setShort(int i, int j) {
        return this.buf.setShort(i, j);
    }

    public ByteBuf setMedium(int i, int j) {
        return this.buf.setMedium(i, j);
    }

    public ByteBuf setInt(int i, int j) {
        return this.buf.setInt(i, j);
    }

    public ByteBuf setLong(int i, long j) {
        return this.buf.setLong(i, j);
    }

    public ByteBuf setChar(int i, int j) {
        return this.buf.setChar(i, j);
    }

    public ByteBuf setFloat(int i, float f) {
        return this.buf.setFloat(i, f);
    }

    public ByteBuf setDouble(int i, double d0) {
        return this.buf.setDouble(i, d0);
    }

    public ByteBuf setBytes(int i, ByteBuf bytebuf) {
        return this.buf.setBytes(i, bytebuf);
    }

    public ByteBuf setBytes(int i, ByteBuf bytebuf, int j) {
        return this.buf.setBytes(i, bytebuf, j);
    }

    public ByteBuf setBytes(int i, ByteBuf bytebuf, int j, int k) {
        return this.buf.setBytes(i, bytebuf, j, k);
    }

    public ByteBuf setBytes(int i, byte[] abyte) {
        return this.buf.setBytes(i, abyte);
    }

    public ByteBuf setBytes(int i, byte[] abyte, int j, int k) {
        return this.buf.setBytes(i, abyte, j, k);
    }

    public ByteBuf setBytes(int i, ByteBuffer bytebuffer) {
        return this.buf.setBytes(i, bytebuffer);
    }

    public int setBytes(int i, InputStream inputstream, int j) throws IOException {
        return this.buf.setBytes(i, inputstream, j);
    }

    public int setBytes(int i, ScatteringByteChannel scatteringbytechannel, int j) throws IOException {
        return this.buf.setBytes(i, scatteringbytechannel, j);
    }

    public ByteBuf setZero(int i, int j) {
        return this.buf.setZero(i, j);
    }

    public boolean readBoolean() {
        return this.buf.readBoolean();
    }

    public byte readByte() {
        return this.buf.readByte();
    }

    public short readUnsignedByte() {
        return this.buf.readUnsignedByte();
    }

    public short readShort() {
        return this.buf.readShort();
    }

    public int readUnsignedShort() {
        return this.buf.readUnsignedShort();
    }

    public int readMedium() {
        return this.buf.readMedium();
    }

    public int readUnsignedMedium() {
        return this.buf.readUnsignedMedium();
    }

    public int readInt() {
        return this.buf.readInt();
    }

    public long readUnsignedInt() {
        return this.buf.readUnsignedInt();
    }

    public long readLong() {
        return this.buf.readLong();
    }

    public char readChar() {
        return this.buf.readChar();
    }

    public float readFloat() {
        return this.buf.readFloat();
    }

    public double readDouble() {
        return this.buf.readDouble();
    }

    public ByteBuf readBytes(int i) {
        return this.buf.readBytes(i);
    }

    public ByteBuf readSlice(int i) {
        return this.buf.readSlice(i);
    }

    public ByteBuf readBytes(ByteBuf bytebuf) {
        return this.buf.readBytes(bytebuf);
    }

    public ByteBuf readBytes(ByteBuf bytebuf, int i) {
        return this.buf.readBytes(bytebuf, i);
    }

    public ByteBuf readBytes(ByteBuf bytebuf, int i, int j) {
        return this.buf.readBytes(bytebuf, i, j);
    }

    public ByteBuf readBytes(byte[] abyte) {
        return this.buf.readBytes(abyte);
    }

    public ByteBuf readBytes(byte[] abyte, int i, int j) {
        return this.buf.readBytes(abyte, i, j);
    }

    public ByteBuf readBytes(ByteBuffer bytebuffer) {
        return this.buf.readBytes(bytebuffer);
    }

    public ByteBuf readBytes(OutputStream outputstream, int i) throws IOException {
        return this.buf.readBytes(outputstream, i);
    }

    public int readBytes(GatheringByteChannel gatheringbytechannel, int i) throws IOException {
        return this.buf.readBytes(gatheringbytechannel, i);
    }

    public ByteBuf skipBytes(int i) {
        return this.buf.skipBytes(i);
    }

    public ByteBuf writeBoolean(boolean flag) {
        return this.buf.writeBoolean(flag);
    }

    public ByteBuf writeByte(int i) {
        return this.buf.writeByte(i);
    }

    public ByteBuf writeShort(int i) {
        return this.buf.writeShort(i);
    }

    public ByteBuf writeMedium(int i) {
        return this.buf.writeMedium(i);
    }

    public ByteBuf writeInt(int i) {
        return this.buf.writeInt(i);
    }

    public ByteBuf writeLong(long i) {
        return this.buf.writeLong(i);
    }

    public ByteBuf writeChar(int i) {
        return this.buf.writeChar(i);
    }

    public ByteBuf writeFloat(float f) {
        return this.buf.writeFloat(f);
    }

    public ByteBuf writeDouble(double d0) {
        return this.buf.writeDouble(d0);
    }

    public ByteBuf writeBytes(ByteBuf bytebuf) {
        return this.buf.writeBytes(bytebuf);
    }

    public ByteBuf writeBytes(ByteBuf bytebuf, int i) {
        return this.buf.writeBytes(bytebuf, i);
    }

    public ByteBuf writeBytes(ByteBuf bytebuf, int i, int j) {
        return this.buf.writeBytes(bytebuf, i, j);
    }

    public ByteBuf writeBytes(byte[] abyte) {
        return this.buf.writeBytes(abyte);
    }

    public ByteBuf writeBytes(byte[] abyte, int i, int j) {
        return this.buf.writeBytes(abyte, i, j);
    }

    public ByteBuf writeBytes(ByteBuffer bytebuffer) {
        return this.buf.writeBytes(bytebuffer);
    }

    public int writeBytes(InputStream inputstream, int i) throws IOException {
        return this.buf.writeBytes(inputstream, i);
    }

    public int writeBytes(ScatteringByteChannel scatteringbytechannel, int i) throws IOException {
        return this.buf.writeBytes(scatteringbytechannel, i);
    }

    public ByteBuf writeZero(int i) {
        return this.buf.writeZero(i);
    }

    public int indexOf(int i, int j, byte b0) {
        return this.buf.indexOf(i, j, b0);
    }

    public int bytesBefore(byte b0) {
        return this.buf.bytesBefore(b0);
    }

    public int bytesBefore(int i, byte b0) {
        return this.buf.bytesBefore(i, b0);
    }

    public int bytesBefore(int i, int j, byte b0) {
        return this.buf.bytesBefore(i, j, b0);
    }

    public int forEachByte(ByteBufProcessor bytebufprocessor) {
        return this.buf.forEachByte(bytebufprocessor);
    }

    public int forEachByte(int i, int j, ByteBufProcessor bytebufprocessor) {
        return this.buf.forEachByte(i, j, bytebufprocessor);
    }

    public int forEachByteDesc(ByteBufProcessor bytebufprocessor) {
        return this.buf.forEachByteDesc(bytebufprocessor);
    }

    public int forEachByteDesc(int i, int j, ByteBufProcessor bytebufprocessor) {
        return this.buf.forEachByteDesc(i, j, bytebufprocessor);
    }

    public ByteBuf copy() {
        return this.buf.copy();
    }

    public ByteBuf copy(int i, int j) {
        return this.buf.copy(i, j);
    }

    public ByteBuf slice() {
        return this.buf.slice();
    }

    public ByteBuf slice(int i, int j) {
        return this.buf.slice(i, j);
    }

    public ByteBuf duplicate() {
        return this.buf.duplicate();
    }

    public int nioBufferCount() {
        return this.buf.nioBufferCount();
    }

    public ByteBuffer nioBuffer() {
        return this.buf.nioBuffer();
    }

    public ByteBuffer nioBuffer(int i, int j) {
        return this.buf.nioBuffer(i, j);
    }

    public ByteBuffer internalNioBuffer(int i, int j) {
        return this.buf.internalNioBuffer(i, j);
    }

    public ByteBuffer[] nioBuffers() {
        return this.buf.nioBuffers();
    }

    public ByteBuffer[] nioBuffers(int i, int j) {
        return this.buf.nioBuffers(i, j);
    }

    public boolean hasArray() {
        return this.buf.hasArray();
    }

    public byte[] array() {
        return this.buf.array();
    }

    public int arrayOffset() {
        return this.buf.arrayOffset();
    }

    public boolean hasMemoryAddress() {
        return this.buf.hasMemoryAddress();
    }

    public long memoryAddress() {
        return this.buf.memoryAddress();
    }

    public String toString(Charset charset) {
        return this.buf.toString(charset);
    }

    public String toString(int i, int j, Charset charset) {
        return this.buf.toString(i, j, charset);
    }

    public int hashCode() {
        return this.buf.hashCode();
    }

    public boolean equals(Object object) {
        return this.buf.equals(object);
    }

    public int compareTo(ByteBuf bytebuf) {
        return this.buf.compareTo(bytebuf);
    }

    public String toString() {
        return this.buf.toString();
    }

    public ByteBuf retain(int i) {
        return this.buf.retain(i);
    }

    public ByteBuf retain() {
        return this.buf.retain();
    }

    public int refCnt() {
        return this.buf.refCnt();
    }

    public boolean release() {
        return this.buf.release();
    }

    public boolean release(int i) {
        return this.buf.release(i);
    }
    @Override
    public ByteBuf asReadOnly() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public int forEachByte(ByteProcessor arg0) {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public int forEachByte(int arg0, int arg1, ByteProcessor arg2) {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public int forEachByteDesc(ByteProcessor arg0) {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public int forEachByteDesc(int arg0, int arg1, ByteProcessor arg2) {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public int getBytes(int arg0, FileChannel arg1, long arg2, int arg3) throws IOException {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public CharSequence getCharSequence(int arg0, int arg1, Charset arg2) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public int getIntLE(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public long getLongLE(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public int getMediumLE(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public short getShortLE(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public long getUnsignedIntLE(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public int getUnsignedMediumLE(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public int getUnsignedShortLE(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public boolean isReadOnly() {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public int readBytes(FileChannel arg0, long arg1, int arg2) throws IOException {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public CharSequence readCharSequence(int arg0, Charset arg1) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public int readIntLE() {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public long readLongLE() {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public int readMediumLE() {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public ByteBuf readRetainedSlice(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public short readShortLE() {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public long readUnsignedIntLE() {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public int readUnsignedMediumLE() {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public int readUnsignedShortLE() {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public ByteBuf retainedDuplicate() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public ByteBuf retainedSlice() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public ByteBuf retainedSlice(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public int setBytes(int arg0, FileChannel arg1, long arg2, int arg3) throws IOException {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public int setCharSequence(int arg0, CharSequence arg1, Charset arg2) {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public ByteBuf setIntLE(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public ByteBuf setLongLE(int arg0, long arg1) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public ByteBuf setMediumLE(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public ByteBuf setShortLE(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public ByteBuf touch() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public ByteBuf touch(Object arg0) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public int writeBytes(FileChannel arg0, long arg1, int arg2) throws IOException {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public int writeCharSequence(CharSequence arg0, Charset arg1) {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public ByteBuf writeIntLE(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public ByteBuf writeLongLE(long arg0) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public ByteBuf writeMediumLE(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public ByteBuf writeShortLE(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }
}
