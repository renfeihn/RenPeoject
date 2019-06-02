package com.renfei.example.nio.server;

/**
 * Created by renfei on 2019/6/1.
 */
//对selectionKey事件的处理

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * description:
 *
 * @author renfei
 */
public interface ServerHandlerBs {


    void accept(SelectionKey selectionKey) throws IOException;

    void close(SelectionKey selectionKey) throws IOException;

    String read(SelectionKey selectionKey) throws IOException;

    String write(SelectionKey selectionKey, String string) throws IOException;



}

