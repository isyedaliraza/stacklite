import type { Tag } from "./tag";

export type Question = {
    id: string;
    title: string;
    body: string;
    answered: boolean;
    tags: Tag[];
};