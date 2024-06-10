import { useEffect, useState } from "react";
import { issueApi } from "../../api/issue";

export const useIssueDetail = (issueId) => {
  const [issue, setIssue] = useState(null);

  const saveProgress = async (e) => {
    const { data } = await issueApi.updateProgress(issueId, issue.progress);
    setIssue(data);
  };

  const updateProgress = (progress) => {
    setIssue((prevIssue) => ({
      ...prevIssue,
      progress,
    }));
  };

  useEffect(() => {
    const fetchData = async () => {
      const { data } = await issueApi.getIssueById(issueId);
      setIssue(data);
    };
    fetchData();
  }, [issueId]);

  return { issue, updateProgress, saveProgress };
};
