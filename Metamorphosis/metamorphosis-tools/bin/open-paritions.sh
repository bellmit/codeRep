#!/bin/bash

# ���һ��topic�����з��� �Ĺرձ��,һ������������رշ�����Ĳ���
# usage:
#      OpenPartitionsTool -topic xxtopic
#      OpenPartitionsTool -topic xxtopic -host 10.2.2.3
#      OpenPartitionsTool -topic xxtopic -port 9999
#      OpenPartitionsTool -topic xxtopic -host 10.2.2.3 -port 9999
 
sh $(dirname $0)/tools-run-class.sh com.taobao.metamorphosis.tools.shell.OpenPartitionsTool $@ 
