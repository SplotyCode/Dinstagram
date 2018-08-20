package team.gutterteam123.netlib.packetbase;

public interface Packet<T> {

    void read(T packet);
    void write(T packet);

}
