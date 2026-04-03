import { fetchTags, updateQuestion } from "@/api";
import type { Question, Tag } from "@/types";
import {
	Button,
	createListCollection,
	Field,
	Fieldset,
	Input,
	Portal,
	Select,
	Stack,
} from "@chakra-ui/react";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { useMemo, useState } from "react";

const EditQuestionForm = ({
	original,
	handleClose,
}: {
	original: Question;
	handleClose: () => void;
}) => {
	const queryClient = useQueryClient();

	const { data = [] } = useQuery({
		queryKey: ["tags"],
		queryFn: fetchTags,
	});

	const tags = useMemo(() => {
		return createListCollection({
			items: data,
			itemToString: (tag) => tag.name,
			itemToValue: (tag) => tag.id,
		});
	}, [data]);

	const [question, setQuestion] = useState<Question>(original);

	const { mutate } = useMutation({
		mutationFn: updateQuestion,
		onSuccess: () => {
			queryClient.invalidateQueries({ queryKey: ["questions"] });
		},
		onError: (err) => {
			console.error(err);
		},
	});

	const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
		setQuestion({ ...question, [event.target.name]: event.target.value });
	};

	const handleChangeTags = (tags: Tag[]) => {
		setQuestion({ ...question, tags: tags });
	};

	const validateQuestion = (): boolean => {
		if (question.title.trim() === "") {
			return false;
		}

		if (question.body.trim() === "") {
			return false;
		}

		return true;
	};

	const handleSave = () => {
		if (validateQuestion()) {
			mutate(question);
			handleClose();
		}
	};

	return (
		<Fieldset.Root size="lg" maxW="md">
			<Stack>
				<Fieldset.HelperText>
					Fill the form to update the question
				</Fieldset.HelperText>
			</Stack>

			<Fieldset.Content>
				<Field.Root>
					<Field.Label>Title</Field.Label>
					<Input name="title" value={question.title} onChange={handleChange} />
				</Field.Root>

				<Field.Root>
					<Field.Label>Body</Field.Label>
					<Input name="body" value={question.body} onChange={handleChange} />
				</Field.Root>

				<Select.Root
					multiple
					collection={tags}
					value={question.tags.map((t) => t.id)}
					onValueChange={(details) => handleChangeTags(details.items)}
				>
					<Select.HiddenSelect />
					<Select.Label>Tags</Select.Label>
					<Select.Control>
						<Select.Trigger>
							<Select.ValueText placeholder="Select tags" />
						</Select.Trigger>
						<Select.IndicatorGroup>
							<Select.Indicator />
						</Select.IndicatorGroup>
					</Select.Control>
					<Portal>
						<Select.Positioner>
							<Select.Content>
								{tags.items.map((tag) => (
									<Select.Item item={tag} key={tag.id}>
										{tag.name}
										<Select.ItemIndicator />
									</Select.Item>
								))}
							</Select.Content>
						</Select.Positioner>
					</Portal>
				</Select.Root>
			</Fieldset.Content>

			<Button alignSelf="flex-start" onClick={handleSave}>
				Submit
			</Button>
		</Fieldset.Root>
	);
};

export default EditQuestionForm;
