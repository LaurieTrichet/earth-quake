package com.laurietrichet.earthquake.com.laurietrichet.earthquake.net;

import java.util.List;

/**
 * Created by laurie on 26/10/2014.
 */
public interface ILightWebServiceClient <T>{

    List <T> get (String url);
}
