package team.gutterteam123.netlib.packetbase;

public interface IPacket<T> extends Packet {

    void read(T packet);
    void write(T packet);

}
