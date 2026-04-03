import { deleteQuestion } from "@/api";
import type { Question } from "@/types";
import { Badge, Flex, IconButton, Table, Wrap } from "@chakra-ui/react";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import {
	createColumnHelper,
	flexRender,
	getCoreRowModel,
	useReactTable,
} from "@tanstack/react-table";
import { useMemo } from "react";
import { FaTrash } from "react-icons/fa";
import EditQuestionButton from "./EditQuestionButton";

const columnHelper = createColumnHelper<Question>();

const QuestionsTable = ({ data }: { data: Question[] }) => {
	const columns = useMemo(
		() => [
			columnHelper.accessor("id", {
				header: "ID",
				cell: (info) => info.getValue(),
			}),
			columnHelper.accessor("title", {
				header: "Title",
				cell: (info) => info.getValue(),
			}),
			columnHelper.accessor("body", {
				header: "Body",
				cell: (info) => info.getValue(),
			}),
			columnHelper.accessor("answered", {
				header: "Is answered?",
				cell: (info) =>
					info.getValue() ? (
						<Badge colorPalette="green">True</Badge>
					) : (
						<Badge colorPalette="red">False</Badge>
					),
			}),
			columnHelper.accessor("tags", {
				header: "Tags",
				cell: (info) => (
					<Wrap>
						{info.getValue().map((tag) => (
							<Badge key={tag.id}>{tag.name}</Badge>
						))}
					</Wrap>
				),
			}),
			columnHelper.display({
				id: "actions",
				header: "Actions",
				cell: (info) => (
					<Flex gap={2}>
						<EditQuestionButton question={info.row.original} />
						<IconButton
							aria-label="Delete question"
							variant="ghost"
							size="sm"
							disabled={info.row.original.answered}
							onClick={() => handleDelete(info.row.original.id)}
						>
							<FaTrash />
						</IconButton>
					</Flex>
				),
			}),
		],
		[],
	);

	const table = useReactTable({
		data,
		columns,
		getCoreRowModel: getCoreRowModel(),
	});

	const queryClient = useQueryClient();

	const { mutate } = useMutation({
		mutationFn: deleteQuestion,
		onSuccess: () => {
			queryClient.invalidateQueries({ queryKey: ["questions"] });
		},
		onError: (err) => {
			console.error(err);
		},
	});

	const handleDelete = (id: string) => {
		const result = confirm("Are you sure you want to delete this question?");
		if (result) {
			mutate(id);
		}
	};

	return (
		<Table.Root variant="outline" borderRadius="lg">
			<Table.Header>
				{table.getHeaderGroups().map((headerGroup) => (
					<Table.Row key={headerGroup.id}>
						{headerGroup.headers.map((header) => (
							<Table.ColumnHeader key={header.id}>
								{flexRender(
									header.column.columnDef.header,
									header.getContext(),
								)}
							</Table.ColumnHeader>
						))}
					</Table.Row>
				))}
			</Table.Header>
			<Table.Body>
				{table.getRowModel().rows.map((row) => (
					<Table.Row key={row.id}>
						{row.getVisibleCells().map((cell) => (
							<Table.Cell key={cell.id}>
								{flexRender(cell.column.columnDef.cell, cell.getContext())}
							</Table.Cell>
						))}
					</Table.Row>
				))}
			</Table.Body>
		</Table.Root>
	);
};

export default QuestionsTable;
