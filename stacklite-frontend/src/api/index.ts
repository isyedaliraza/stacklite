import axios from "axios";

import type { Question, Tag } from "@/types";

const addQuestion = async (question: Question): Promise<Question> => {
    const response = await axios.post(`${import.meta.env.VITE_API_URL}/api/v1/questions`, 
        {...question, tags: question.tags.map((t) => t.id)}, {
        headers: { 'Content-Type': 'application/json', },
    });
    return response.data;
};

const updateQuestion = async (question: Question): Promise<Question> => {
    const response = await axios.put(`${import.meta.env.VITE_API_URL}/api/v1/questions/${question.id}`, 
        {...question, tags: question.tags.map((t) => t.id)}, {
        headers: { 'Content-Type': 'application/json', },
    });
    return response.data;
};

const deleteQuestion = async (id: string) => {
    await axios.delete(`${import.meta.env.VITE_API_URL}/api/v1/questions/${id}`);
};

const fetchQuestions = async (): Promise<Question[]> => {
    try {
        const response = await axios.get(`${import.meta.env.VITE_API_URL}/api/v1/questions`);
        return response.data;
    }
    catch (error) {
        console.error("Error fetching questions:", error);
        return [];
    }
};

const fetchTags = async (): Promise<Tag[]> => {
    try {
        const response = await axios.get(`${import.meta.env.VITE_API_URL}/api/v1/tags`);
        return response.data;
    }
    catch (error) {
        console.error("Error fetching tags:", error);
        return [];
    }
};

export {
    fetchQuestions,
    fetchTags,
    addQuestion,
    deleteQuestion,
    updateQuestion
};