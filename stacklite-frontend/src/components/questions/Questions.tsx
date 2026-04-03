import type { Question } from "@/types";
import { useQuery } from "@tanstack/react-query";
import { fetchQuestions } from "@/api";
import QuestionsLoading from "./QuestionsLoading";
import QuestionsError from "./QuestionsError";
import QuestionsEmpty from "./QuestionsEmpty";
import QuestionsTable from "./QuestionsTable";
import { Flex, Heading } from "@chakra-ui/react";
import NewQuestionButton from "./NewQuestionButton";

function Questions() {
	const {
		data = [],
		error,
		isError,
		isLoading,
		isSuccess,
	} = useQuery({
		queryKey: ["questions"],
		queryFn: fetchQuestions,
	});

	if (isLoading) {
		return <QuestionsLoading />;
	}

	if (isError) {
		return <QuestionsError message={error.message} />;
	}

	if (isSuccess && data.length == 0) {
		return <QuestionsEmpty />;
	}

	return (
		<Flex direction="column" gap={4} flex={1}>
			<Flex
				padding={4}
				bgColor="gray.100"
				borderRadius="lg"
				justifyContent="space-between"
			>
				<Heading>Questions</Heading>
				<NewQuestionButton />
			</Flex>
			<QuestionsTable data={data} />
		</Flex>
	);
}

export default Questions;
