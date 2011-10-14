#
# makefile.
#

PACKAGE= ls
NAME= lsl
SRCS= $(PACKAGE).c
HEADS= $(PACKAGE).h
OBJS= $(SRCS:.c=.o)

# common (*.o)
LD= gcc
LDFLAGS= -g $(DEBUG)
LDLIBS= -lm

# *.c
CC = gcc

# run
$(PACKAGE):$(OBJS)
	$(CC) -o $(NAME) $(SRCS)
