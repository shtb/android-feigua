TEMPLATE = app
CONFIG += console
CONFIG -= app_bundle
CONFIG -= qt

QMAKE_CXXFLAGS+=-std=c++11 -pthread -Werror
QMAKE_LFLAGS+=-pthread /usr/lib64/libmysqlclient.so

SOURCES += \
    database.cpp \
    interfaces.cpp \
    main.cpp \
    inte_link.cpp

include(deployment.pri)
qtcAddDeployment()

HEADERS += \
    database.h \
    interfaces.h \
    inte_link.h

