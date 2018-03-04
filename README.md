# OpenTracing Finagle Instrumentation

TODO

Example trace of an HTTP service calling a Thrift service
```
0304 11:31:10.320 e1b1010b7453845e.e1b1010b7453845e<:e1b1010b7453845e] Rpc(GET)
0304 11:31:10.326 e1b1010b7453845e.e1b1010b7453845e<:e1b1010b7453845e] BinaryAnnotation(http.uri,/)
0304 11:31:10.327 e1b1010b7453845e.e1b1010b7453845e<:e1b1010b7453845e] ServiceName(finagle-http)
0304 11:31:10.327 e1b1010b7453845e.e1b1010b7453845e<:e1b1010b7453845e] BinaryAnnotation(srv/finagle.version,18.2.0)
0304 11:31:10.327 e1b1010b7453845e.e1b1010b7453845e<:e1b1010b7453845e] ServerRecv()
0304 11:31:10.328 e1b1010b7453845e.e1b1010b7453845e<:e1b1010b7453845e] LocalAddr(/172.19.0.8:8080)
0304 11:31:10.329 e1b1010b7453845e.e1b1010b7453845e<:e1b1010b7453845e] ServerAddr(/172.19.0.8:8080)
0304 11:31:10.329 e1b1010b7453845e.e1b1010b7453845e<:e1b1010b7453845e] ClientAddr(/172.19.0.1:52306)
0304 11:31:10.349 e1b1010b7453845e.c523d6162a0e03af<:e1b1010b7453845e] ServiceName(greeting-service)
0304 11:31:10.349 e1b1010b7453845e.c523d6162a0e03af<:e1b1010b7453845e] BinaryAnnotation(clnt/finagle.version,18.2.0)
0304 11:31:10.349 e1b1010b7453845e.c523d6162a0e03af<:e1b1010b7453845e] ClientSend()
0304 11:31:10.418 e1b1010b7453845e.c523d6162a0e03af<:e1b1010b7453845e] ClientAddr(/172.19.0.8:35246)
0304 11:31:10.516 e1b1010b7453845e.c523d6162a0e03af<:e1b1010b7453845e] WireSend
0304 11:31:10.517 e1b1010b7453845e.c523d6162a0e03af<:e1b1010b7453845e] Rpc(greet)
0304 11:31:10.542 e1b1010b7453845e.c523d6162a0e03af<:e1b1010b7453845e] ServerAddr(finagle-thrift/172.19.0.4:8080)
0304 11:31:10.542 e1b1010b7453845e.c523d6162a0e03af<:e1b1010b7453845e] ClientAddr(/172.19.0.8:35246)
0304 11:31:10.595 e1b1010b7453845e.c523d6162a0e03af<:e1b1010b7453845e] WireRecv
0304 11:31:10.603 e1b1010b7453845e.c523d6162a0e03af<:e1b1010b7453845e] ClientRecv()
0304 11:31:10.607 e1b1010b7453845e.e1b1010b7453845e<:e1b1010b7453845e] ServerSend()
```

Similar trace when the Thrift service times out
```
0304 11:45:40.054 08f302d8bafd09e2.08f302d8bafd09e2<:08f302d8bafd09e2] Rpc(GET)
0304 11:45:40.059 08f302d8bafd09e2.08f302d8bafd09e2<:08f302d8bafd09e2] BinaryAnnotation(http.uri,/)
0304 11:45:40.059 08f302d8bafd09e2.08f302d8bafd09e2<:08f302d8bafd09e2] ServiceName(finagle-http)
0304 11:45:40.059 08f302d8bafd09e2.08f302d8bafd09e2<:08f302d8bafd09e2] BinaryAnnotation(srv/finagle.version,18.2.0)
0304 11:45:40.060 08f302d8bafd09e2.08f302d8bafd09e2<:08f302d8bafd09e2] ServerRecv()
0304 11:45:40.060 08f302d8bafd09e2.08f302d8bafd09e2<:08f302d8bafd09e2] LocalAddr(/172.19.0.8:8080)
0304 11:45:40.061 08f302d8bafd09e2.08f302d8bafd09e2<:08f302d8bafd09e2] ServerAddr(/172.19.0.8:8080)
0304 11:45:40.061 08f302d8bafd09e2.08f302d8bafd09e2<:08f302d8bafd09e2] ClientAddr(/172.19.0.1:52486)
0304 11:45:40.076 08f302d8bafd09e2.9cbf7bd8bd7a45d0<:08f302d8bafd09e2] ServiceName(greeting-service)
0304 11:45:40.076 08f302d8bafd09e2.9cbf7bd8bd7a45d0<:08f302d8bafd09e2] BinaryAnnotation(clnt/finagle.version,18.2.0)
0304 11:45:40.076 08f302d8bafd09e2.9cbf7bd8bd7a45d0<:08f302d8bafd09e2] ClientSend()
0304 11:45:40.145 08f302d8bafd09e2.9cbf7bd8bd7a45d0<:08f302d8bafd09e2] ClientAddr(/172.19.0.8:35426)
0304 11:45:40.157 08f302d8bafd09e2.9cbf7bd8bd7a45d0<:08f302d8bafd09e2] WireSend
0304 11:45:40.158 08f302d8bafd09e2.9cbf7bd8bd7a45d0<:08f302d8bafd09e2] Rpc(greet)
0304 11:45:40.183 08f302d8bafd09e2.9cbf7bd8bd7a45d0<:08f302d8bafd09e2] ServerAddr(finagle-thrift/172.19.0.4:8080)
0304 11:45:40.183 08f302d8bafd09e2.9cbf7bd8bd7a45d0<:08f302d8bafd09e2] ClientAddr(/172.19.0.8:35426)
0304 11:45:45.192 08f302d8bafd09e2.9cbf7bd8bd7a45d0<:08f302d8bafd09e2] Message(finagle.timeout)
0304 11:45:45.199 08f302d8bafd09e2.9cbf7bd8bd7a45d0<:08f302d8bafd09e2] WireRecvError(com.twitter.util.TimeoutException: 5.seconds)
0304 11:45:45.200 08f302d8bafd09e2.9cbf7bd8bd7a45d0<:08f302d8bafd09e2] WireRecv
0304 11:45:45.211 08f302d8bafd09e2.9cbf7bd8bd7a45d0<:08f302d8bafd09e2] ClientRecvError(com.twitter.finagle.IndividualRequestTimeoutException: exceeded 5.seconds to greeting-service while waiting for a response for an individual request, excluding retries. Remote Info: Upstream Address: Not Available, Upstream id: Not Available, Downstream Address: finagle-thrift/172.19.0.4:8080, Downstream label: greeting-service, Trace Id: 08f302d8bafd09e2.9cbf7bd8bd7a45d0<:08f302d8bafd09e2)
0304 11:45:45.211 08f302d8bafd09e2.9cbf7bd8bd7a45d0<:08f302d8bafd09e2] ClientRecv()
0304 11:45:45.216 08f302d8bafd09e2.08f302d8bafd09e2<:08f302d8bafd09e2] ServerSendError(com.twitter.finagle.IndividualRequestTimeoutException: exceeded 5.seconds to finagle-http while waiting for a response for an individual request, excluding retries. Remote Info: Upstream Address: Not Available, Upstream id: Not Available, Downstream Address: finagle-thrift/172.19.0.4:8080, Downstream label: greeting-service, Trace Id: 08f302d8bafd09e2.9cbf7bd8bd7a45d0<:08f302d8bafd09e2)
0304 11:45:45.216 08f302d8bafd09e2.08f302d8bafd09e2<:08f302d8bafd09e2] ServerSend()
```

All spans end with either `ClientRecv` or `ServerSend`.
