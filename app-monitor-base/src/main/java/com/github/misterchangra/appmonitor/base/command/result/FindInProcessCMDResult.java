package com.github.misterchangra.appmonitor.base.command.result;

import com.github.misterchangra.appmonitor.base.command.BaseCMDResult;

import java.util.List;

public class FindInProcessCMDResult  extends BaseCMDResult {
    private List<ProcessResult> processResultList;

    public FindInProcessCMDResult(List<ProcessResult> processResultList) {
        this.processResultList = processResultList;
    }

    public List<ProcessResult> getProcessResultList() {
        return processResultList;
    }

    public void setProcessResultList(List<ProcessResult> processResultList) {
        this.processResultList = processResultList;
    }

    public static class ProcessResult {
        private String name;
        private String pid;
        private String memUseKB;

        public ProcessResult(String name, String pid, String memUse) {
            this.name = name;
            this.pid = pid;
            this.memUseKB = memUse;
        }

        public ProcessResult() {

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getMemUseKB() {
            return memUseKB;
        }

        public void setMemUseKB(String memUseKB) {
            this.memUseKB = memUseKB;
        }
    }

}
